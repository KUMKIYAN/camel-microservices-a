package com.kiyan.microservices.camelmicroservicea.routes.activemq;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class ActiveMQSenderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:active-mq-timer?period=10000")
                .transform().constant("My message for active MQ")
                .log("${body}")
                .to("activemq:my-activemq-queue");
    }
}
