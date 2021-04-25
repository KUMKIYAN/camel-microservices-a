package com.kiyan.microservices.camelmicroservicea.routes.eippatterns;


import com.kiyan.microservices.camelmicroservicea.model.Employee;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class EipPatternAggregatorPatternRouters extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file://src/test/data/xml-inbox?noop=true")
                .unmarshal()
                .jacksonxml(Employee.class)
                .aggregate(simple("${body.name}"), new ArrayListAggregationStratergy())
                .completionSize(2)
                .to("log:output-employee-log");
    }
}

@Component
    class  ArrayListAggregationStratergy implements AggregationStrategy{

      @Override
      public Exchange aggregate(Exchange oldValue, Exchange newValue){
        Object newBaby = newValue.getIn().getBody();
        ArrayList<Object> list = null;
        if(oldValue == null){
            list = new ArrayList<Object>();
            list.add(newBaby);
            newValue.getIn().setBody(list);
            return newValue;
        } else {
            list = oldValue.getIn().getBody(ArrayList.class);
            list.add(newBaby);
            return oldValue;
        }
      }
    }
