package com.kiyan.microservices.camelmicroservicea.routes.eippatterns;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class EipPatternSplitSinglelineRouters extends RouteBuilder {

    @Autowired
    private SingleLineExchangeTransformer singleLineExchangeTransformer;

//    @Override
//    public void configure() throws Exception {
//        from("file://src/test/data/singleline?noop=true")
//              .convertBodyTo(String.class)
//              .split(method(singleLineExchangeTransformer))
//              .to("log:output-single-line");
//    }

    @Override
    public void configure() throws Exception {
        from("file://src/test/data/singleline?noop=true")
                .convertBodyTo(String.class)
              .split(body(),",")
                .to("log:output-log");
    }
}

@Component
class SingleLineExchangeTransformer {
    public List<String> transfrom(String body) {
        List<String> words = new ArrayList<>();
        String[] mywords =  body.split(",");
        for (String a : mywords) {
            words.add(a);
        }
        return words;
    }
}
