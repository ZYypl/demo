package com.example.demo.comment;

import com.alibaba.fastjson.JSON;

/**
 * com.example.demo.comment
 *
 * @author ypl
 * @create 2020-06-16 09:40
 */
public class ApiResult {

    private Boolean result;
    private Object data;
    private String message;

    public ApiResult() {
    }

    public ApiResult(Boolean result) {
        this.result = result;
    }

    public ApiResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public ApiResult(Boolean result, Object data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
