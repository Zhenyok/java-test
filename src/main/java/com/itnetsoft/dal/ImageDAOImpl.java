package com.itnetsoft.dal;


import com.itnetsoft.enums.FieldAccess;
import com.itnetsoft.enums.FieldType;
import com.itnetsoft.exceptions.ImagePersistenceException;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.UsersEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class ImageDAOImpl implements IImageDAO{

    @Inject
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ContentEntity> getManagerImageList() throws ImagePersistenceException {
        Session session = null;
        int id = 0;
        try {
            session = getCurrentSession();
            List<ContentEntity> result = null;
            result = session.createCriteria(ContentEntity.class)
                    .add(Restrictions.eq("contentType", "IMAGE"))
                    .addOrder(Order.desc("id"))
                    .list();
            return result;
        } catch(HibernateException e) {
            throw new ImagePersistenceException(e);
        }
    }

    @Override
    public boolean deleteImageItem(int id) throws ImagePersistenceException {
        if (id <= 0) {
            return false;
        }
        Session session = null;
        int deleted = 0;
        try {
            session = getCurrentSession();
            deleted = session.createQuery("DELETE FROM ContentEntity WHERE id=:id AND contentType=:content")
                    .setInteger("id", id)
                    .setParameter("content", "IMAGE")
                    .executeUpdate();
            if (deleted == 1) {
                session.createQuery("UPDATE SettingsEntity SET value=:value WHERE fieldAccess=:access AND " +
                        "fieldType=:content AND value LIKE :search")
                        .setString("value","")
                        .setParameter("access", FieldAccess.COMMON)
                        .setParameter("content", FieldType.IMAGE)
                        .setParameter("search","%\"id\":"+id+"%")
                        .executeUpdate();
                return true;
            }
            return false;
        } catch(HibernateException e) {
            throw new ImagePersistenceException(e);
        }
    }

    @Override
    public <T> ContentEntity  getItem(Class<T> item, int id) throws ImagePersistenceException {
        if (id <= 0){
            return null;
        }
        Session session = null;
        try {
            session = getCurrentSession();

            return (ContentEntity) session.get(item, id);
        } catch(HibernateException e) {
            throw new ImagePersistenceException(e);
        }
    }

    @Override
    public boolean saveImageItem(ContentEntity saveConvertedItem) throws ImagePersistenceException {
        if (saveConvertedItem == null) {
            return false;
        }
        Session session = null;
        try {
            session = getCurrentSession();
            saveConvertedItem.setContentType("IMAGE");
            session.update(saveConvertedItem);
            return true;
        } catch(HibernateException e) {
            throw new ImagePersistenceException(e);
        }
    }

    @Override
    public List<ContentEntity> uploadImageFiles(List<ContentEntity> files, UsersEntity author) throws ImagePersistenceException {
        if (files == null || files.size() == 0) {
            return null;
        }
        Session session = null;
        int id = 0;
        try {
            session = getCurrentSession();
            int count = 0;
            for(ContentEntity file : files) {
                if (StringUtils.isBlank(file.getUrl())) {
                    continue;
                }
                file.setAuthor(author);
                session.save(file);
                if (++count % 20 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            return files;
        } catch(HibernateException e) {
            throw new ImagePersistenceException(e);
        }
    }

}
