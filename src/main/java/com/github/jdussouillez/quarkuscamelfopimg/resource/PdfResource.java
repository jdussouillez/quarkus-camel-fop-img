package com.github.jdussouillez.quarkuscamelfopimg.resource;

import com.github.jdussouillez.quarkuscamelfopimg.service.PdfService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Path("/pdf")
public class PdfResource {

    private static final String RESOURCE_DIR = "/pdf";

    @Inject
    protected PdfService pdfService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] generate() throws IOException {
        return pdfService.generate(getFile("cars.xsl"), getFile("cars.xml"));
    }

    @GET
    @Path("/img")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] generateWithImg() throws IOException {
        return pdfService.generate(getFile("cars-img.xsl"), getFile("cars-img.xml"));
    }

    private static File getFile(final String filename) throws IOException {
        var res = Thread.currentThread().getContextClassLoader().getResource(RESOURCE_DIR + "/" + filename);
        if (res == null) {
            throw new IOException("Resource not found");
        }
        try {
            return new File(res.toURI());
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
    }
}
