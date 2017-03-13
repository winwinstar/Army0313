package com.wss.study.army.dto;

/**
 * Created by Administrator on 2017-01-23.
 */
public class SeckillResult<T> {
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String errors) {
        this.success = success;
        this.errors = errors;
    }

    private boolean success;

    private T data;

    private String errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
