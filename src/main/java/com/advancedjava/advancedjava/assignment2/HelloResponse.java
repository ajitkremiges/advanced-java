package com.advancedjava.advancedjava.assignment2;

import java.time.LocalDateTime;


public class HelloResponse {
    private String status;
    private String status_code;
    private String status_msg;
    private String data;
    private String reqid;
    private LocalDateTime server_ts;
    private String client_type;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus_code() {
        return status_code;
    }
    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }
    public String getStatus_msg() {
        return status_msg;
    }
    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getReqid() {
        return reqid;
    }
    public void setReqid(String reqid) {
        this.reqid = reqid;
    }
    public LocalDateTime getServer_ts() {
        return server_ts;
    }
    public void setServer_ts(LocalDateTime server_ts) {
        this.server_ts = server_ts;
    }
    public String getClient_type() {
        return client_type;
    }
    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }
      
}
