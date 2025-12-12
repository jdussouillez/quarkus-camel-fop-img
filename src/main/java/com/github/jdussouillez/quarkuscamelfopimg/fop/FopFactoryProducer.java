package com.github.jdussouillez.quarkuscamelfopimg.fop;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;

@ApplicationScoped
public class FopFactoryProducer {

    private static final String RESOURCE_DIR = "/pdf";

    @Produces
    @Named("fopFactory")
    public FopFactory fopFactory() {
        try (var in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE_DIR + "/fop.xconf")) {
            var cfg = new DefaultConfigurationBuilder()
                .build(in);
            var baseURI = getClass().getResource(RESOURCE_DIR + "/").toURI();
            var fopFactory = new FopFactoryBuilder(baseURI)
                .setConfiguration(cfg)
                .build();
            fopFactory.getFontManager()
                .setResourceResolver(ResourceResolverFactory.createDefaultInternalResourceResolver(baseURI));
            return fopFactory;
        } catch (IOException | URISyntaxException | ConfigurationException ex) {
            throw new RuntimeException("Error when loading FOP", ex);
        }
    }
}
