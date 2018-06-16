package com.itnetsoft.exceptions;


public class UserPersistenceException extends Exception{
    private static final long serialVersionUID = 1223L;
    public UserPersistenceException(Throwable e) {
        super(e);
    }

}
