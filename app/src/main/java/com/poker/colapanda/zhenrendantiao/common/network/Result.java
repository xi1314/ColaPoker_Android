package com.poker.colapanda.zhenrendantiao.common.network;

/**
 * 网络类的封装
 * Created by xiaomengli on 16/5/17.
 */
public class Result<T> {
    private boolean success;
    private T data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final int RESULT_OK = 2000;

    public boolean getSuccess() {
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


}

