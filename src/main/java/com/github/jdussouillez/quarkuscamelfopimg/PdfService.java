package com.github.jdussouillez.quarkuscamelfopimg;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class PdfService {

    private static final String DATA_DIR = "src/main/resources/pdf";

    @Inject
    protected ProducerTemplate producerTemplate;

    public byte[] generate(final boolean includeImg) {
        return generatePdf(generateFopXml(includeImg));
    }

    private String generateFopXml(final boolean includeImg) {
        var xmlFile = new File(DATA_DIR, "data.xml");
        try (var xsl = new FileInputStream(new File(DATA_DIR, "template.xsl"))) {
            Map<String, Object> headers = Map.of(
                "CamelXsltStylesheet", xsl,
                "includeImg", String.valueOf(includeImg)
            );
            return producerTemplate.requestBodyAndHeaders(
                "xslt-saxon?contentCache=false&allowTemplateFromHeader=true", xmlFile, headers, String.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error generating FOP XML", ex);
        }
    }

    private byte[] generatePdf(final String fopXml) {
        return producerTemplate.requestBody("fop:application/pdf", fopXml, byte[].class);
    }
}
