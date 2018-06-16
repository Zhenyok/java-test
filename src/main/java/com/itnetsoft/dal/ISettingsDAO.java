package com.itnetsoft.dal;



import com.itnetsoft.exceptions.SettingsPersistenceException;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.SettingsEntity;
import com.itnetsoft.hibernate.entities.UsersEntity;

import java.util.List;

public interface ISettingsDAO {
    public List<SettingsEntity> getSettingsList() throws SettingsPersistenceException;
    public boolean deleteSetting(int id) throws SettingsPersistenceException;
    public SettingsEntity addSettingItem(SettingsEntity item) throws SettingsPersistenceException;
    public List<SettingsEntity> saveSettingsList(List<SettingsEntity> itemsList) throws SettingsPersistenceException;
    public <T> ContentEntity  getItem(Class<T> item, int id) throws  SettingsPersistenceException;
}
