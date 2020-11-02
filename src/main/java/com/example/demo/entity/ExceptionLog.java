package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * com.example.demo.entity
 *
 * @author ypl
 * @create 2020-11-02 09:53
 */

@Table(name="exception_log")
public class ExceptionLog implements Serializable {

    @Column(name = "excep_id")
    private String excepId;

    @Column(name = "excep_request_param")
    private String excepRequestParam;

    @Column(name = "excep_name")
    private String excepName;

    @Column(name = "excep_message")
    private String excepMessage;

    @Column(name = "oper_user_id")
    private String operUserId;

    @Column(name = "oper_user_name")
    private String operUserName;

    @Column(name = "oper_url")
    private String operUrl;

    @Column(name = "oper_method")
    private String operMethod;

    @Column(name = "oper_ip")
    private String operIp;

    @Column(name = "oper_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date operTime;

    public ExceptionLog() {
    }

    public ExceptionLog(String excepId, String excepRequestParam, String excepName, String excepMessage, String operUserId, String operUserName, String operUrl, String operMethod, String operIp, Date operTime) {
        this.excepId = excepId;
        this.excepRequestParam = excepRequestParam;
        this.excepName = excepName;
        this.excepMessage = excepMessage;
        this.operUserId = operUserId;
        this.operUserName = operUserName;
        this.operUrl = operUrl;
        this.operMethod = operMethod;
        this.operIp = operIp;
        this.operTime = operTime;
    }

    public String getExcepId() {
        return excepId;
    }

    public void setExcepId(String excepId) {
        this.excepId = excepId;
    }

    public String getExcepRequestParam() {
        return excepRequestParam;
    }

    public void setExcepRequestParam(String excepRequestParam) {
        this.excepRequestParam = excepRequestParam;
    }

    public String getExcepName() {
        return excepName;
    }

    public void setExcepName(String excepName) {
        this.excepName = excepName;
    }

    public String getExcepMessage() {
        return excepMessage;
    }

    public void setExcepMessage(String excepMessage) {
        this.excepMessage = excepMessage;
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperMethod() {
        return operMethod;
    }

    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}
