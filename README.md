# quarkus-camel-fop-img

Project to reproduce a bug with images when generating PDF files using FOP with Apache Camel run by Quarkus.

## Documentation

### JAR mode

#### Build & run

```sh
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

#### Test

```sh
curl -s -f http://localhost:8081/pdf -o jar.pdf || echo "Failed to generate"
curl -s -f http://localhost:8081/pdf?includeImg=true -o jar-img.pdf || echo "Failed to generate"
```

### Native mode

#### Build & run

```sh
./mvnw package -Dnative
./target/quarkus-camel-fop-img-0.0.0-SNAPSHOT-runner
```

#### Test

```sh
curl -s -f http://localhost:8081/pdf -o native.pdf || echo "Failed to generate"
curl -s -f http://localhost:8081/pdf?includeImg=true -o native-img.pdf || echo "Failed to generate"
```

## Results

Everything works as expected except the PDF with image in native mode.

Stacktrace:
```
org.apache.camel.CamelExecutionException: Exception occurred during execution on the exchange: Exchange[]
	at org.apache.camel.support.ExchangeHelper.extractResultBody(ExchangeHelper.java:701)
	at org.apache.camel.impl.engine.DefaultProducerTemplate.extractResultBody(DefaultProducerTemplate.java:594)
	at org.apache.camel.impl.engine.DefaultProducerTemplate.extractResultBody(DefaultProducerTemplate.java:590)
	at org.apache.camel.impl.engine.DefaultProducerTemplate.requestBody(DefaultProducerTemplate.java:415)
	at com.github.jdussouillez.quarkuscamelfopimg.PdfService.generate(PdfService.java:14)
	at com.github.jdussouillez.quarkuscamelfopimg.PdfService_ClientProxy.generate(Unknown Source)
	at com.github.jdussouillez.quarkuscamelfopimg.PdfResource.generate(PdfResource.java:23)
	at com.github.jdussouillez.quarkuscamelfopimg.PdfResource$quarkusrestinvoker$generate_eb31acc0e950bd2216d13cbc6581cec34976181f.invoke(Unknown Source)
	at org.jboss.resteasy.reactive.server.handlers.InvocationHandler.handle(InvocationHandler.java:29)
	at io.quarkus.resteasy.reactive.server.runtime.QuarkusResteasyReactiveRequestContext.invokeHandler(QuarkusResteasyReactiveRequestContext.java:183)
	at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)
	at io.quarkus.vertx.core.runtime.VertxCoreRecorder$15.runWith(VertxCoreRecorder.java:645)
	at org.jboss.threads.EnhancedQueueExecutor$Task.doRunWith(EnhancedQueueExecutor.java:2651)
	at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2630)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1622)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1589)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base@21.0.9/java.lang.Thread.runWith(Thread.java:1596)
	at java.base@21.0.9/java.lang.Thread.run(Thread.java:1583)
	at org.graalvm.nativeimage.builder/com.oracle.svm.core.thread.PlatformThreads.threadStartRoutine(PlatformThreads.java:896)
	at org.graalvm.nativeimage.builder/com.oracle.svm.core.thread.PlatformThreads.threadStartRoutine(PlatformThreads.java:872)
Caused by: javax.xml.transform.TransformerException: java.util.MissingResourceException: Error reading event-model.xml: org.xml.sax.SAXException: Could not find Class for: java.io.FileNotFoundException
java.lang.ClassNotFoundException: java.io.FileNotFoundException
	at java.xml@21.0.9/com.sun.org.apache.xalan.internal.xsltc.trax.TransformerImpl.transform(TransformerImpl.java:790)
	at java.xml@21.0.9/com.sun.org.apache.xalan.internal.xsltc.trax.TransformerImpl.transform(TransformerImpl.java:395)
	at org.apache.camel.component.fop.FopProducer.transform(FopProducer.java:98)
	at org.apache.camel.component.fop.FopProducer.process(FopProducer.java:66)
	at org.apache.camel.support.AsyncProcessorConverterHelper$ProcessorToAsyncProcessorBridge.process(AsyncProcessorConverterHelper.java:65)
	at org.apache.camel.impl.engine.SharedCamelInternalProcessor.processNonTransacted(SharedCamelInternalProcessor.java:156)
	at org.apache.camel.impl.engine.SharedCamelInternalProcessor.process(SharedCamelInternalProcessor.java:133)
	at org.apache.camel.impl.engine.SharedCamelInternalProcessor$1.process(SharedCamelInternalProcessor.java:89)
	at org.apache.camel.impl.engine.DefaultAsyncProcessorAwaitManager.process(DefaultAsyncProcessorAwaitManager.java:82)
	at org.apache.camel.impl.engine.SharedCamelInternalProcessor.process(SharedCamelInternalProcessor.java:86)
	at org.apache.camel.support.cache.DefaultProducerCache.send(DefaultProducerCache.java:180)
	at org.apache.camel.impl.engine.DefaultProducerTemplate.send(DefaultProducerTemplate.java:175)
	at org.apache.camel.impl.engine.DefaultProducerTemplate.send(DefaultProducerTemplate.java:171)
	at org.apache.camel.impl.engine.DefaultProducerTemplate.requestBody(DefaultProducerTemplate.java:413)
	... 19 more
Caused by: java.util.MissingResourceException: Error reading event-model.xml: org.xml.sax.SAXException: Could not find Class for: java.io.FileNotFoundException
java.lang.ClassNotFoundException: java.io.FileNotFoundException
	at org.apache.fop.events.DefaultEventBroadcaster.loadModel(DefaultEventBroadcaster.java:91)
	at org.apache.fop.events.DefaultEventBroadcaster.getEventProducerModel(DefaultEventBroadcaster.java:113)
	at org.apache.fop.events.DefaultEventBroadcaster.createProxyFor(DefaultEventBroadcaster.java:141)
	at org.apache.fop.events.DefaultEventBroadcaster.getEventProducerFor(DefaultEventBroadcaster.java:128)
	at org.apache.fop.ResourceEventProducer$Provider.get(ResourceEventProducer.java:53)
	at org.apache.fop.fo.flow.ExternalGraphic.bind(ExternalGraphic.java:83)
	at org.apache.fop.fo.FObj.processNode(FObj.java:131)
	at org.apache.fop.fo.FOTreeBuilder$MainFOHandler.startElement(FOTreeBuilder.java:321)
	at org.apache.fop.fo.FOTreeBuilder$2.run(FOTreeBuilder.java:185)
	at org.apache.fop.fo.FOTreeBuilder$2.run(FOTreeBuilder.java:182)
	at java.base@21.0.9/java.security.AccessController.executePrivileged(AccessController.java:129)
	at java.base@21.0.9/java.security.AccessController.doPrivileged(AccessController.java:319)
	at org.apache.fop.fo.FOTreeBuilder.startElement(FOTreeBuilder.java:181)
	at java.xml@21.0.9/com.sun.org.apache.xml.internal.serializer.ToXMLSAXHandler.closeStartTag(ToXMLSAXHandler.java:206)
	at java.xml@21.0.9/com.sun.org.apache.xml.internal.serializer.ToSAXHandler.flushPending(ToSAXHandler.java:250)
	at java.xml@21.0.9/com.sun.org.apache.xml.internal.serializer.ToXMLSAXHandler.endElement(ToXMLSAXHandler.java:245)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.parsers.AbstractSAXParser.endElement(AbstractSAXParser.java:618)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.parsers.AbstractXMLDocumentParser.emptyElement(AbstractXMLDocumentParser.java:184)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl.scanStartElement(XMLNSDocumentScannerImpl.java:353)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl$FragmentContentDriver.next(XMLDocumentFragmentScannerImpl.java:2726)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.impl.XMLDocumentScannerImpl.next(XMLDocumentScannerImpl.java:605)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.impl.XMLNSDocumentScannerImpl.next(XMLNSDocumentScannerImpl.java:114)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl.scanDocument(XMLDocumentFragmentScannerImpl.java:542)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:889)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.parsers.XML11Configuration.parse(XML11Configuration.java:825)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.parsers.XMLParser.parse(XMLParser.java:141)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.parsers.AbstractSAXParser.parse(AbstractSAXParser.java:1224)
	at java.xml@21.0.9/com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl$JAXPSAXParser.parse(SAXParserImpl.java:637)
	at java.xml@21.0.9/com.sun.org.apache.xalan.internal.xsltc.trax.TransformerImpl.transformIdentity(TransformerImpl.java:667)
	at java.xml@21.0.9/com.sun.org.apache.xalan.internal.xsltc.trax.TransformerImpl.transform(TransformerImpl.java:781)
```

Related code:
- [FOP's `DefaultEventBroadcaster.loadModel`](https://github.com/apache/xmlgraphics-fop/blob/2_11/fop-events/src/main/java/org/apache/fop/events/DefaultEventBroadcaster.java#L80)
- [Camel Quarkus FOP's `FopProcessor.initResources`](https://github.com/apache/camel-quarkus/blob/3.30.0/extensions/fop/deployment/src/main/java/org/apache/camel/quarkus/component/fop/deployment/FopProcessor.java#L101)
