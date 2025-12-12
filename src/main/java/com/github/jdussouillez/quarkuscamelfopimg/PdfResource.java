package com.github.jdussouillez.quarkuscamelfopimg;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/pdf")
public class PdfResource {

    @Inject
    protected PdfService pdfService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] generate(@QueryParam("includeImg") @DefaultValue("false") final boolean includeImg) {
        try {
            return pdfService.generate(includeImg);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
