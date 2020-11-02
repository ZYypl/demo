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
 * @create 2020-11-02 09:50
 */
@Table(name = "operation_log")
public class OperationLog implements Serializable {

    @Column(name = "oper_id")
    private String operId;
    @Column(name = "oper_type")
    private String operType;
    @Column(name = "oper_model")
    private String operModel;
    @Column(name = "oper_desc")
    private String operDesc;
    @Column(name = "oper_request_param")
    private String operRequestParam;
    @Column(name = "oper_response_param")
    private String operResponseParam;
    @Column(name = "oper_user_id")
    private String operUserId;
    @Column(name = "oper_user_name")
    private String operUserName;
    @Column(name = "oper_method")
    private String operMethod;
    @Column(name = "oper_url")
    private String operUrl;
    @Column(name = "oper_ip")
    private String operIp;
    @Column(name = "oper_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date operTime;

    public OperationLog() {
    }

    public OperationLog(String operId, String operType, String operModel, String operDesc, String operRequestParam, String operResponseParam, String operUserId, String operUserName, String operMethod, String operUrl, String operIp, Date operTime) {
        this.operId = operId;
        this.operType = operType;
        this.operModel = operModel;
        this.operDesc = operDesc;
        this.operRequestParam = operRequestParam;
        this.operResponseParam = operResponseParam;
        this.operUserId = operUserId;
        this.operUserName = operUserName;
        this.operMethod = operMethod;
        this.operUrl = operUrl;
        this.operIp = operIp;
        this.operTime = operTime;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperModel() {
        return operModel;
    }

    public void setOperModel(String operModel) {
        this.operModel = operModel;
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
    }

    public String getOperRequestParam() {
        return operRequestParam;
    }

    public void setOperRequestParam(String operRequestParam) {
        this.operRequestParam = operRequestParam;
    }

    public String getOperResponseParam() {
        return operResponseParam;
    }

    public void setOperResponseParam(String operResponseParam) {
        this.operResponseParam = operResponseParam;
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

    public String getOperMethod() {
        return operMethod;
    }

    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
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
