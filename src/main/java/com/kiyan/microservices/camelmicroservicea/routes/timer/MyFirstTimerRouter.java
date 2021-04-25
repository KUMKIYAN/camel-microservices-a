package com.kiyan.microservices.camelmicroservicea.routes.timer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")  //timer is keyword and name of the timer is first-timer
                .log("${body}")
                //.transform().constant("kiyandoor "+ LocalDateTime.now()) // what ever output coming from will be transform to constant and giving it to
                //.bean("getCurrentTimeBean")
                //.bean(getCurrentTimeBean) // when we have only one method no need to specify the method name
                .bean(getCurrentTimeBean, "getCurrentTime1")
                .log("${body}")
                .bean(simpleLoggingProcessingComponent)
                .log("${body}")
                .bean(new SimpleLoggingProcess())
                .log("${body}")
                .to("log:first-timer:"); // log is keyword again and name of the log is first-timer
    }
}

@Component
class GetCurrentTimeBean{
    public String getCurrentTime(){
        return "time now is " + LocalDateTime.now();
    }

    public String getCurrentTime1(){
        return "Kiyan now is " + LocalDateTime.now();
    }
}


@Component
class SimpleLoggingProcessingComponent{

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
    public void process(String message){
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}


class SimpleLoggingProcess implements Processor {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info( "SimpleLoggingProcess {}", exchange.getMessage().getBody());
    }
}