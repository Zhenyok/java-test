package com.itnetsoft.dal;

import com.itnetsoft.exceptions.ContentPersistenceException;
import com.itnetsoft.hibernate.entities.TestimonialsEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class ContentDAOImpl implements IContentDAO {
    @Inject
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TestimonialsEntity saveTestimonial(TestimonialsEntity item) throws ContentPersistenceException {
        if (item == null ) {
            return null;
        }
        Session session = null;
        try {
            session = getCurrentSession();
            session.saveOrUpdate(item);
            return item;
        } catch(HibernateException e) {
            throw new ContentPersistenceException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TestimonialsEntity> getTestimonialList() throws ContentPersistenceException {
        Session session = null;
        try {
            session = getCurrentSession();
            return session.createCriteria(TestimonialsEntity.class)
                    .addOrder(Order.desc("id"))
                    .list();
        } catch(HibernateException e) {
            throw new ContentPersistenceException(e);
        }
    }

    @Override
    public boolean delTestimonial(int id) throws ContentPersistenceException {
        Session session = null;
        try {
            session = getCurrentSession();
            if (id <= 0) {
                return false;
            }
            TestimonialsEntity tmnl = (TestimonialsEntity) session.get(TestimonialsEntity.class, id);
            if (tmnl == null) {
                return false;
            }
            session.delete(tmnl);
            return true;
        } catch(HibernateException e) {
            throw new ContentPersistenceException(e);
        }
    }

    @Override
    public boolean delTestimonial(List<TestimonialsEntity> entityList) throws ContentPersistenceException {
        Session session = null;
        try {
            session = getCurrentSession();
            if (entityList == null || entityList.size() == 0) {
                return false;
            }
            int count = 0;
            for(TestimonialsEntity item : entityList) {
                session.delete(item);
                if (++count % 20 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            return true;
        } catch(HibernateException e) {
            throw new ContentPersistenceException(e);
        }
    }
}
