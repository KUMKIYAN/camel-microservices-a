package com.kiyan.microservices.camelmicroservicea.routes.eippatterns;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.Map;

@Component
public class EipPatternDynamicRouting extends RouteBuilder {

    @Autowired
    private DynamicRoutingBean dynamicRoutingBean;

    @Override
    public void configure() throws Exception {


        from("timer:dynamicRouting?period=10000")
                .transform().constant("myMessage is XYZ")
                .dynamicRouter(method(dynamicRoutingBean));

        from("direct:endpoint1")
                .to("log:directEndpoint1");

        from("direct:endpoint2")
                .to("log:directEndpoint2");

        from("direct:endpoint3")
                .to("log:directEndpoint3");
    }


}


@Component
class DynamicRoutingBean {
    Logger logger = LoggerFactory.getLogger(DynamicRoutingBean.class);

    int invocation;
    public String decideTheNextEndPoint(@ExchangeProperties Map<String, String> properties,
                                        @Headers Map<String, String> headers,
                                        @Body String body){

    invocation++;
    if(invocation%3 == 0)
        return "direct:endpoint1";
    if(invocation%3 == 1)
        return "direct:endpoint2, direct:endpoint3";

    return null;

    }

}