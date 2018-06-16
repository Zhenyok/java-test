package com.itnetsoft.dal;

import com.itnetsoft.exceptions.UserPersistenceException;
import com.itnetsoft.hibernate.entities.UsersEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class UserDAOImpl implements IUserDAO {
    @Inject
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public UsersEntity getUserByName(String name)   throws UserPersistenceException{
        Session session = null;
        UsersEntity userDetails = null;
        try {
            session = getCurrentSession();
            userDetails = (UsersEntity) session.createCriteria(UsersEntity.class)
                                                .add(Restrictions.eq("login", name))
                                                .uniqueResult();
            return userDetails;
        } catch(HibernateException e) {
            throw new UserPersistenceException(e);
        }
    }
}
