package com.github.jdussouillez.quarkuscamelfopimg.resource;

import com.github.jdussouillez.quarkuscamelfopimg.service.PdfService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/pdf")
public class PdfResource {

    @Inject
    protected PdfService pdfService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] generate() throws IOException {
        return pdfService.generate("cars.xsl", "cars.xml");
    }

    @GET
    @Path("/img")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] generateWithImg() throws IOException {
        return pdfService.generate("cars-img.xsl", "cars-img.xml");
    }
}
