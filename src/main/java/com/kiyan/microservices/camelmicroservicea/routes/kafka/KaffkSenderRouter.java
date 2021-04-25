package com.kiyan.microservices.camelmicroservicea.routes.kafka;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class KaffkSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://src/test/data/xml-inbox?noop=true")
        .to("kafka:myKafkaTopic")
                .to("log:received-message-from-kafka");
    }
}
