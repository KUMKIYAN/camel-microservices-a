package com.kiyan.microservices.camelmicroservicea.routes.eippatterns;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class EipPatternMultiCastRouters extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:multicast?period=10000")
                .multicast()
                .to("log:someQueue1","log:someQueue2","log:someQueue3");
    }
}
