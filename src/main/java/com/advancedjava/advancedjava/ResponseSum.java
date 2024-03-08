package com.advancedjava.advancedjava;

import java.time.LocalDateTime;

public class ResponseSum {
    private double result;
    private String clientType;
    private LocalDateTime currentTime;


    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "ResponseSum [result=" + result + ", clientType=" + clientType + ", currentTime=" + currentTime + "]";
    }

   

}
