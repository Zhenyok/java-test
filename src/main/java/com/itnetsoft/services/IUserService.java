package com.itnetsoft.services;


import com.itnetsoft.exceptions.UserServiceException;
import com.itnetsoft.hibernate.entities.UsersEntity;

public interface IUserService {
    public UsersEntity getUserByName(String name) throws UserServiceException;
}
