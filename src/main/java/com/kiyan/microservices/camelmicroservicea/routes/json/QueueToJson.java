package com.kiyan.microservices.camelmicroservicea.routes.json;

import com.kiyan.microservices.camelmicroservicea.model.Employee;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class QueueToJson extends RouteBuilder {
    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

    @Autowired
    private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    @Override
    public void configure() throws Exception {
        from("file://src/test/data/xml-inbox?noop=true")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, Employee.class)
                .bean(myCurrencyExchangeProcessor)
                .bean(myCurrencyExchangeTransformer)
                .to("log:received-message-from-active-mq");
    }
}

@Component
class MyCurrencyExchangeProcessor {
    private Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);
    public void processMessage(Employee employee){
        logger.info("employee info is " + employee.getName());
    }
}


@Component
class MyCurrencyExchangeTransformer{
    private Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeTransformer.class);
    private static int id = 100;
    public Employee TransformMessage(Employee employee){
        employee.setEmployeeid(id++);
        return employee;
    }
}