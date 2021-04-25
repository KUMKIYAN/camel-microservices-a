package com.kiyan.microservices.camelmicroservicea.routes.eippatterns;


import com.kiyan.microservices.camelmicroservicea.model.Employee;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//@Component
public class EipPatternSplitRouters extends RouteBuilder {
    @Autowired
    private EmployeeExchangeTransformer employeeExchangeTransformer;
    @Override
    public void configure() throws Exception {
        from("file://src/test/data/employee?noop=true")
                .unmarshal().csv()
                .split(body())
                .log("${body}")
                .multicast()
                .bean(method(employeeExchangeTransformer))
                .to("log:someQueue1","log:someQueue2","log:someQueue3");
    }
}

@Component
class EmployeeExchangeTransformer{
    private Logger logger = LoggerFactory.getLogger(EmployeeExchangeTransformer.class);

    public Employee TransformMessage(String body){
        Employee employee = new Employee();
        employee.setEmployeeid(Integer.parseInt(body.split(",")[0]));
        employee.setName(body.split(",")[1]);
        return employee;
    }
}
