package com.itnetsoft.dal;


import com.itnetsoft.exceptions.ImagePersistenceException;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.UsersEntity;

import java.util.List;

public interface IImageDAO {
    public List<ContentEntity> getManagerImageList() throws ImagePersistenceException;
    public <T> ContentEntity  getItem(Class<T> item, int id) throws  ImagePersistenceException;
    public boolean deleteImageItem(int id) throws ImagePersistenceException;
    public boolean saveImageItem(ContentEntity saveConvertedItem) throws ImagePersistenceException;
    public List<ContentEntity> uploadImageFiles(List<ContentEntity> files, UsersEntity user) throws ImagePersistenceException;




}
