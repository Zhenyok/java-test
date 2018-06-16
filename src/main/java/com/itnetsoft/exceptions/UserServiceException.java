package com.itnetsoft.exceptions;

public class UserServiceException extends Exception{
    private static final long serialVersionUID = 1123L;
    public UserServiceException(Throwable e) {
        super(e);
    }

}
