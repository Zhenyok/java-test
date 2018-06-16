package com.itnetsoft.jsonDTO;


import java.io.Serializable;

public class ResponseHttpError implements Serializable {
    private int error;
    private String message;

    public ResponseHttpError(){}

    public ResponseHttpError(int error, String message) {
        setError(error);
        setMessage(message);
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
}
