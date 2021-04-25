package com.kiyan.microservices.camelmicroservicea.routes.xml;

import com.kiyan.microservices.camelmicroservicea.model.Employee;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class QueueToXml extends RouteBuilder {
    @Autowired
    private MyCurrencyExchangeXMLProcessor myCurrencyExchangeXMLProcessor;

    @Autowired
    private MyCurrencyExchangeXMLTransformer myCurrencyExchangeXMLTransformer;

    @Override
    public void configure() throws Exception {
        from("file://src/test/data/xml-inbox?noop=true")
                .log("${body}")
                .unmarshal()
                .jacksonxml(Employee.class)
                .log("${body}")
                .bean(myCurrencyExchangeXMLProcessor)
                .bean(myCurrencyExchangeXMLTransformer)
                .to("log:received-message-from-active-mq");
    }
}

@Component
class MyCurrencyExchangeXMLProcessor {
    private Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeXMLProcessor.class);
    public void processMessage(Employee employee){
        logger.info("employee info is " + employee.getName());
    }
}


@Component
class MyCurrencyExchangeXMLTransformer{
    private Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeXMLTransformer.class);
    private static int id = 10000;
    public Employee TransformMessage(Employee employee){
        employee.setEmployeeid(id++);
        employee.setName("Arjun Kiyan");
        return employee;
    }
}