package com.github.jdussouillez.quarkuscamelfopimg;

import java.io.FileNotFoundException;

import org.apache.fop.ResourceEventProducer;
import org.apache.xmlgraphics.image.loader.ImageException;
import org.apache.xmlgraphics.image.loader.impl.imageio.PreloaderImageIO;

import io.quarkus.runtime.annotations.RegisterForProxy;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {FileNotFoundException.class,
		ImageException.class,
		PreloaderImageIO.class,
		})
@RegisterForProxy(targets = ResourceEventProducer.class)
public class RegisterExtraNativeConfiguration {

}
