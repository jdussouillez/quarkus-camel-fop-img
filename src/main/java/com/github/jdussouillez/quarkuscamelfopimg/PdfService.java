package com.github.jdussouillez.quarkuscamelfopimg;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
public class PdfService {

    @Inject
    protected ProducerTemplate producerTemplate;

    public byte[] generate(final boolean includeImg) {
        return producerTemplate.requestBody("fop:application/pdf", generateFopXml(includeImg), byte[].class);
    }

    private String generateFopXml(final boolean includeImg) {
        return """
            <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
                <fo:layout-master-set>
                    <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21cm">
                        <fo:region-body/>
                    </fo:simple-page-master>
                </fo:layout-master-set>
                <fo:page-sequence master-reference="A4">
                    <fo:flow flow-name="xsl-region-body">
                        <fo:block font-size="14pt" font-family="serif">Hello, World!</fo:block>
                        <fo:block>%s</fo:block>
                    </fo:flow>
                </fo:page-sequence>
            </fo:root>
            """.formatted(includeImg ? generateImg() : "no img");
    }

    private String generateImg() {
        return """
            <fo:external-graphic
                content-width="150pt"
                content-height="150pt"
                src="url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==')"/>
            """;
    }
}
