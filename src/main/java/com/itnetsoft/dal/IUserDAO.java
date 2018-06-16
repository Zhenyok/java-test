package com.itnetsoft.dal;


import com.itnetsoft.exceptions.UserPersistenceException;
import com.itnetsoft.hibernate.entities.UsersEntity;

public interface IUserDAO {
    public UsersEntity getUserByName(String name)  throws UserPersistenceException;
}
