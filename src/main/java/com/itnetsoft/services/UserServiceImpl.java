package com.itnetsoft.services;


import com.itnetsoft.dal.IUserDAO;
import com.itnetsoft.exceptions.UserPersistenceException;
import com.itnetsoft.exceptions.UserServiceException;
import com.itnetsoft.hibernate.entities.UsersEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Inject
    private IUserDAO userDAO;

    @Override
    public UsersEntity getUserByName(String name) throws UserServiceException {
        try {
            if (StringUtils.isBlank(name)) {
                return null;
            }
            return userDAO.getUserByName(name);
        } catch(UserPersistenceException e) {
            throw new UserServiceException(e);
        }


    }
}
