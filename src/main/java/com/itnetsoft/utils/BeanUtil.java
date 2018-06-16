package com.itnetsoft.utils;


import com.itnetsoft.dto.FileUploadDTO;
import com.itnetsoft.dto.ImageManagerDTO;
import com.itnetsoft.dto.SettingsDTO;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.SettingsEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeanUtil {

    public static  <S, T> List<S> beanConvert(List<T> items, Class<S> itemClass){
        try {
            if(items ==null || items.size() == 0) {
                return null;
            }
            List<S> settingsList = new ArrayList<>(items.size());
            for(T item:items) {
                S tmpBean = itemClass.newInstance();
                BeanUtils.copyProperties(tmpBean, item);
                settingsList.add(tmpBean);
            }
            return settingsList;
        } catch(InvocationTargetException | IllegalAccessException|InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <S, T> S beanConvert(T item, Class<S> clazz) {
        try {
            if(item ==null ) {
                return null;
            }
            S entity = clazz.newInstance();
            BeanUtils.copyProperties(entity, item);
            return entity;
        } catch(InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  List<SettingsEntity> beanConvertUtil(List<SettingsDTO> itemsList){
        try {
            if(itemsList == null || itemsList.size() == 0) {
                return null;
            }
            List<SettingsEntity> entityList = new ArrayList<>(itemsList.size());
            for (SettingsDTO item:itemsList) {
                if (StringUtils.isBlank(item.getFieldName())) {
                    continue;
                }
                SettingsEntity tmpObject = new SettingsEntity();
                BeanUtils.copyProperties(tmpObject, item);
                entityList.add(tmpObject);
            }
            return entityList;
        } catch(InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<FileUploadDTO> beanConvert(List<MultipartFile> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        List<FileUploadDTO> result = new ArrayList<>(files.size());
        for(MultipartFile file:files) {
            if (StringUtils.isBlank(file.getContentType()) ||
                    !file.getContentType().toLowerCase().matches("^(image/[\\w]{3,5})$")) {
                continue;
            }
                FileUploadDTO tmpObj = new FileUploadDTO();
                tmpObj.setContentType("IMAGE");
                tmpObj.setDateAdded(new Date());
                tmpObj.setFile(file);
                tmpObj.setFileSize(file.getSize());
                tmpObj.setTitle(file.getOriginalFilename().replaceAll("(.[\\w]{3,5})$",""));
                tmpObj.setFileExtension(file.getContentType().toLowerCase().replaceAll("^(image/)",""));
                result.add(tmpObj);
        }
        return result;
    }


}
