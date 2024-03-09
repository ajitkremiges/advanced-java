package com.advancedjava.advancedjava.assignment1;

import java.time.LocalDateTime;

import com.advancedjava.advancedjava.assignment2.HelloData;



public class HelloRequest {
    private String token;
    private HelloData data;
    private String reqid;
    private LocalDateTime client_ts;
    private String client_type;
    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public HelloData getData() {
        return data;
    }
    public void setData(HelloData data) {
        this.data = data;
    }
    public String getReqid() {
        return reqid;
    }
    public void setReqid(String reqid) {
        this.reqid = reqid;
    }
    public LocalDateTime getClient_ts() {
        return client_ts;
    }
    public void setClient_ts(LocalDateTime client_ts) {
        this.client_ts = client_ts;
    }
    public String getClient_type() {
        return client_type;
    }
    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }
    
}

