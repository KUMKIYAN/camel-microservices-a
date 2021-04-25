package com.kiyan.microservices.camelmicroservicea.routes.http;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class HttprRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost").port(8080);

        from("timer:rest-api-consumer?period=5000")
                .setHeader( "name", () -> "kiyandoor")
                .setHeader( "id", () -> 2001)
                .log("${body}")
                .to("rest:get:/employee/name/{name}/id/{id}")
                .log("${body}");
    }
}
