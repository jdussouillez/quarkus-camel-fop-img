package com.github.jdussouillez.quarkuscamelfopimg.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class PdfService {

    @Inject
    protected ProducerTemplate producerTemplate;

    public void generate(final File xsl, final File xml, final String output) throws IOException {
        var file = Files.createFile(Path.of(".", output));
        Files.write(file, generatePdf(generateFopXml(xsl, xml)), StandardOpenOption.TRUNCATE_EXISTING);
    }

    protected String generateFopXml(final File xsl, final File xml) throws IOException {
        try (var stream = new FileInputStream(xsl)) {
            // https://camel.apache.org/components/4.14.x/xslt-component.html#_dynamic_stylesheets
            Map<String, Object> headers = Map.of("CamelXsltStylesheet", stream);
            return producerTemplate.requestBodyAndHeaders(
                "xslt-saxon?contentCache=false&allowTemplateFromHeader=true",
                xml,
                headers,
                String.class
            );
        } catch (CamelExecutionException ex) {
            throw new IOException("Error when generating FOP XML", ex);
        }
    }

    protected byte[] generatePdf(final String fopXml) {
        return producerTemplate.requestBody(
            "fop:application/pdf?fopFactory=#fopFactory",
            fopXml,
            byte[].class
        );
    }
}
