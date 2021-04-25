package com.kiyan.microservices.camelmicroservicea.routes.eippatterns;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class EipPatternRoutingSlip extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        String routeSlip = "direct:endpoint1,direct:endpoint2";
        String routeSlip2 = "direct:endpoint1,direct:endpoint2,direct:endpoint3";

        from("timer:routingSlip?period=10000")
                .transform().constant("myMessage is XYZ")
                .routingSlip(simple(routeSlip2));

        from("direct:endpoint1")
                .to("log:directEndpoint1");

        from("direct:endpoint2")
                .to("log:directEndpoint2");

        from("direct:endpoint3")
                .to("log:directEndpoint3");
    }
}
