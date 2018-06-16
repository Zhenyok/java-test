package com.itnetsoft.jsonDTO;


public class Response <T> {
    private int error;
    private String message;
    private T data;

    public Response(int error, String message, T data) {
        setError(error);
        setMessage(message);
        setData(data);
    }
    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
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
}
