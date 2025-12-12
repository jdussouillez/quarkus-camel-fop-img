package com.github.jdussouillez.quarkuscamelfopimg.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Map;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class PdfService {

    @Inject
    protected ProducerTemplate producerTemplate;

    public byte[] generate(final String xslFilename, final String xmlFilename) throws IOException {
        return generatePdf(generateFopXml(xslFilename, xmlFilename));
    }

    protected String generateFopXml(final String xslFilename, final String xmlFilename) throws IOException {
        try (var xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("pdf/" + xslFilename);
            var xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("pdf/" + xmlFilename)) {
            // https://camel.apache.org/components/4.14.x/xslt-component.html#_dynamic_stylesheets
            Map<String, Object> headers = Map.of("CamelXsltStylesheet", xslStream);
            return producerTemplate.requestBodyAndHeaders(
                "xslt-saxon?contentCache=false&allowTemplateFromHeader=true",
                xmlStream,
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
