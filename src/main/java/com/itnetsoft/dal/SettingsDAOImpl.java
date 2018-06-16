package com.itnetsoft.dal;

import com.itnetsoft.dto.SettingsDTO;
import com.itnetsoft.enums.FieldAccess;
import com.itnetsoft.enums.FieldType;
import com.itnetsoft.exceptions.SettingsPersistenceException;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.SettingsEntity;
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
public class SettingsDAOImpl implements ISettingsDAO{

    @Inject
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<SettingsEntity> getSettingsList() throws SettingsPersistenceException {
        Session session = null;
        List<SettingsEntity> settingsList = null;
        try {
            session = getCurrentSession();
            settingsList =  session.createCriteria(SettingsEntity.class)
                                    .list();
            return settingsList;
        } catch(HibernateException e) {
            throw new SettingsPersistenceException(e);
        }
    }

    @Override
    public boolean deleteSetting(int id)  throws SettingsPersistenceException {
        if (id <=0 ) {
            return false;
        }
        int deleted = 0;
        Session session = null;
        try {
            session = getCurrentSession();
            deleted = session.createQuery("DELETE FROM SettingsEntity WHERE id=:id AND fieldAccess=:access")
                    .setInteger("id", id)
                    .setParameter("access", FieldAccess.COMMON)
                    .executeUpdate();
            if (deleted == 1) {
                return true;
            }
            return false;
        } catch(HibernateException e) {
            throw new SettingsPersistenceException(e);
        }
    }

    @Override
    public SettingsEntity addSettingItem(SettingsEntity item)  throws SettingsPersistenceException {
        if (item == null || FieldAccess.COMMON !=item.getFieldAccess() || StringUtils.isBlank(item.getFieldName()) ||
                item.getId() > 0) {
            return null;
        }
        Session session = null;
        int id = 0;
        try {
            session = getCurrentSession();
            session.save(item);
            return item;
        } catch(HibernateException e) {
            throw new SettingsPersistenceException(e);
        }
    }

    @Override
    public List<SettingsEntity> saveSettingsList(List<SettingsEntity> itemsList) throws SettingsPersistenceException {
        if (itemsList == null || itemsList.size() == 0) {
            return null;
        }
        Session session = null;
        int id = 0;
        try {
            session = getCurrentSession();
            int count = 0;
            for(SettingsEntity item : itemsList) {
                session.saveOrUpdate(item);
                if (++count % 20 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            return itemsList;
        } catch(HibernateException e) {
            throw new SettingsPersistenceException(e);
        }
    }

    @Override
    public <T> ContentEntity  getItem(Class<T> item, int id) throws SettingsPersistenceException {
        if (id <= 0){
            return null;
        }
        Session session = null;
        try {
            session = getCurrentSession();

            return (ContentEntity) session.get(item, id);
        } catch(HibernateException e) {
            throw new SettingsPersistenceException(e);
        }
    }
}
