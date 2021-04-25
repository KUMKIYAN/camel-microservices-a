package com.kiyan.microservices.camelmicroservicea.routes.kafka;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class KaffkReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:myKafkaTopic")
                .to("log:received-message-from-kafka");
    }
}
