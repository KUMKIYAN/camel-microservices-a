package com.kiyan.microservices.camelmicroservicea.routes.simplelanguagewithdirect;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class SimpleLanguageWithBean extends RouteBuilder {

    @Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {
        from("file://src/test/data/simple")
                .routeId("FILES-INPUT-ROUTES")
                .transform().body(String.class)
                .choice()
                    .when(simple("${file:ext} ends with 'xml'"))
                    .log("XML FILE")
                    .when(method(deciderBean))
                    .log("Not An XML File but contains Kiyan")
                .otherwise()
                    .log("Not An XML file")
                .end()
                .log("${messageHistory}")
                .to("file://src/test/data/out");



    }

}

@Component
class DeciderBean {

    private Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet (@Body String body,
                                       @Headers Map<String, String> headers,
                                       @ExchangeProperties Map<String, String> exchangeProperties){
        logger.info("Decider bean");
        logger.info("Body {}", body);
        logger.info("Headers {}", headers);
        headers.put("kiyan", "kumar");
        logger.info("Headers {}", headers);
        logger.info("Exchange Properties {}", exchangeProperties);
        return true;
    }


}