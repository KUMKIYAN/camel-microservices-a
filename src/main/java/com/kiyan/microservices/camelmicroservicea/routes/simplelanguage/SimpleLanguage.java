package com.kiyan.microservices.camelmicroservicea.routes.simplelanguage;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class SimpleLanguage extends RouteBuilder {

    //https://camel.apache.org/components/latest/languages/simple-language.html

    @Override
    public void configure() throws Exception {
        from("file://src/test/data/simple")
                .routeId("FILES-INPUT-ROUTES")
                .transform().body(String.class)
                .choice()
                    .when(simple("${file:ext} ends with 'xml'"))
                    .log("XML FILE")
                    .when(simple("${body} contains 'Kiyan'"))
                    .log("Not An XML File but contains Kiyan")
                .otherwise()
                    .log("Not An XML file")
                .end()
                .log("${messageHistory}")
                .to("file://src/test/data/out");
    }
}
