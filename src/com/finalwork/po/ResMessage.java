package com.finalwork.po;

/**
 * 回复消息类
 */
public class ResMessage<T> {
    public static final String RES_SUCCESS = "success";
    public static final String RES_ERROR = "error";
    private String status;
    private String message;
    private T data;

    public ResMessage() {
        this.status = RES_ERROR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResMessage{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
