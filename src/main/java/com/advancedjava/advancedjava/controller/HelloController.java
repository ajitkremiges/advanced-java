package com.advancedjava.advancedjava.controller;

import org.springframework.web.bind.annotation.RestController;

import com.advancedjava.advancedjava.assignment1.HelloRequest;
import com.advancedjava.advancedjava.assignment2.HelloResponse;
import com.advancedjava.advancedjava.assignment3.RequestSum;
import com.advancedjava.advancedjava.assignment3.ResponseSum;
import com.advancedjava.advancedjava.assignment4.RequestCalc;
import com.advancedjava.advancedjava.assignment4.ResponseCalc;
import com.advancedjava.advancedjava.assignment5.RequestProperties;
import com.advancedjava.advancedjava.assignment5.ResponseProperties;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
@PropertySource("classpath:application.properties") 
public class HelloController {


    @GetMapping("/hello")
    public HelloResponse sayHello(@RequestParam(value = "name", defaultValue = "World") String name,
                                  @RequestParam(value = "client_type", defaultValue = "web") String clientType) {
        HelloResponse response = new HelloResponse();
        response.setStatus("success");
        response.setStatus_code("");
        response.setStatus_msg("");
        response.setData("Hello " + name);
        response.setReqid(UUID.randomUUID().toString());
        response.setServer_ts(LocalDateTime.now());
        response.setClient_type(clientType); 
        
        return response;
    }

    @PostMapping("/hello")
    public HelloResponse sayHello(@RequestBody HelloRequest request) {
        String name = request.getData().getName();
        String clientType = request.getClient_type();

        HelloResponse response = new HelloResponse();
        response.setStatus("success");
        response.setStatus_code("");
        response.setStatus_msg("");
        response.setData("Hello " + name);
        response.setReqid(UUID.randomUUID().toString());
        response.setServer_ts(LocalDateTime.now());
        response.setClient_type(clientType); 
        
        return response;
    }

    @PostMapping("/mysum")
    public ResponseSum calculate(@RequestBody RequestSum request) {
        double num1 = request.getNum1();
        double num2 = request.getNum2();
        String operation = request.getOperation();
        double result;

        if (request.getOperation() == null || request.getOperation().isEmpty()) {
            throw new IllegalArgumentException("Operation parameter is required");
        }

        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        ResponseSum response = new ResponseSum();
        response.setResult(result);
        response.setClientType(request.getClientType());
        response.setCurrentTime(LocalDateTime.now()); 
        return response;
    }

    @PostMapping("/mycalc")
public ResponseCalc performCalculation(@RequestBody RequestCalc request) {
    List<Double> numbers = request.getNumbers();
    String operation = request.getOperation();
    double result;
    
    switch (operation) {
        case "mean":
            result = calculateMean(numbers);
            break;
        case "min":
            result = calculateMin(numbers);
            break;
        case "max":
            result = calculateMax(numbers);
            break;
        default:
            throw new IllegalArgumentException("Invalid operation: " + operation);
    }
    ResponseCalc response = new ResponseCalc();
    response.setOperation(operation);
    response.setResult(result);

    return response;
}

private double calculateMean(List<Double> numbers) {
    double sum = 0;
    for (double num : numbers) {
        sum += num;
    }
    return sum / numbers.size();
}

private double calculateMin(List<Double> numbers) {
    return Collections.min(numbers);
}

private double calculateMax(List<Double> numbers) {
    return Collections.max(numbers);
}

private final Environment environment;

public HelloController(Environment environment) {
    this.environment = environment;
}

    @SuppressWarnings("null")
    @PostMapping("/myproperties")
    public ResponseProperties getProperties(@RequestBody RequestProperties request) {
        List<String> propertyIdentifiers = request.getPropertyIdentifiers();
        Map<String, String> propertiesMap = new HashMap<>();

        for (String identifier : propertyIdentifiers) {
            String value = environment.getProperty(identifier);
            propertiesMap.put(identifier, value != null ? value : "NULL");
        }
        ResponseProperties response = new ResponseProperties();
        response.setProperties(propertiesMap);

        return response;
    }
}    



 

