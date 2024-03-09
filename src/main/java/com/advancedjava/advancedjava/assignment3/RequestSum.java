package com.advancedjava.advancedjava.assignment3;

public class RequestSum {
    private String operation;
    private double num1;
    private double num2;
    private String clientType;
    
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public double getNum1() {
        return num1;
    }
    public void setNum1(double num1) {
        this.num1 = num1;
    }
    public double getNum2() {
        return num2;
    }
    public void setNum2(double num2) {
        this.num2 = num2;
        
    }
    public String getClientType() {
        return clientType;
    }
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    @Override
    public String toString() {
        return "RequestSum [operation=" + operation + ", num1=" + num1 + ", num2=" + num2 + ", clientType=" + clientType
                + "]";
    }
 

}
