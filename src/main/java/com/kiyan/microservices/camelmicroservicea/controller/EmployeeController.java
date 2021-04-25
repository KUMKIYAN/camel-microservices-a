package com.kiyan.microservices.camelmicroservicea.controller;

import com.kiyan.microservices.camelmicroservicea.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @GetMapping("/employee/name/{name}/id/{id}")
    public Employee findEmployee(@PathVariable String name, @PathVariable int id){
        return new Employee(id, name);
    }
}
