package com.itnetsoft.services;


import com.itnetsoft.dal.ISettingsDAO;
import com.itnetsoft.dto.*;
import com.itnetsoft.exceptions.SettingsPersistenceException;
import com.itnetsoft.exceptions.SettingsServiceException;
import com.itnetsoft.hibernate.entities.SettingsEntity;
import com.itnetsoft.jsonDTO.Response;
import com.itnetsoft.utils.BeanUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import java.util.List;

@Transactional
@Service
public class SettingsServiceImpl implements ISettingsService {

    @Inject
    private ISettingsDAO settingsDAO;

    @Inject
    private MessageSource messages;

    @Override
    public Response<List<SettingsDTO>> getSettingsList() throws SettingsServiceException {
        List<SettingsDTO> settingsObject = null;
        List<SettingsEntity> settingsDAOList = null;
        try {
            settingsDAOList = settingsDAO.getSettingsList();
            if (settingsDAOList != null && settingsDAOList.size() > 0) {
                settingsObject = BeanUtil.beanConvert(settingsDAOList, SettingsDTO.class);
                return new Response<List<SettingsDTO>>(0, "", settingsObject);
            } else {
                return new Response<List<SettingsDTO>>(1, "", null);
            }
        } catch(SettingsPersistenceException e){
            throw new SettingsServiceException(e);
        }
    }


    @Override
    public Response<SettingsDTO> deleteSetting(int id) throws SettingsServiceException {
        boolean result = false;
        try {
            result = settingsDAO.deleteSetting(id);
            if (result == true) {
                return new Response<SettingsDTO>(0, messages.getMessage("page.settings.message.delete_success", null,
                                LocaleContextHolder.getLocale()), null);
            }
            return new Response<SettingsDTO>(1, messages.getMessage("page.settings.message.delete_error",
                    null, LocaleContextHolder.getLocale()), null);
        } catch(SettingsPersistenceException e) {
            throw new SettingsServiceException(e);
        }
    }

    @Override
    public Response<SettingsDTO> addSettingItem(SettingsDTO item) throws SettingsServiceException {
        SettingsDTO savedItem = null;
        SettingsEntity entity = null;
        try {
            entity = settingsDAO.addSettingItem(BeanUtil.beanConvert(item, SettingsEntity.class));
            if (entity == null || entity.getId() <= 0 ) {
                return new Response<SettingsDTO>(1, messages.getMessage("page.settings.message.add_error",
                        null, LocaleContextHolder.getLocale()), null);
            }

            savedItem = BeanUtil.beanConvert(entity, SettingsDTO.class);

            if (savedItem != null && savedItem.getId() > 0 ) {
                return new Response<SettingsDTO>(0, messages.getMessage("page.settings.message.add_success",
                        null,LocaleContextHolder.getLocale()), savedItem);
            } else {
                return new Response<SettingsDTO>(1, messages.getMessage("page.settings.message.add_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
        } catch(SettingsPersistenceException e) {
            throw new SettingsServiceException(e);
        }
    }

    @Override
    public Response<List<SettingsDTO>> saveSettingsList(List<SettingsDTO> itemsList) throws SettingsServiceException {
        List<SettingsEntity> entityList = null;
        List<SettingsEntity> persistedList = null;
        List<SettingsDTO> resultList = null;
        entityList = BeanUtil.beanConvertUtil(itemsList);
        if (entityList == null || entityList.size() == 0) {
            return new Response<List<SettingsDTO>>(1, messages.getMessage("page.settings.message.save_error",
                    null, LocaleContextHolder.getLocale()), null);
        }

        try {
            persistedList = settingsDAO.saveSettingsList(entityList);

            if (persistedList == null || persistedList.size() == 0) {
                return new Response<List<SettingsDTO>>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }

            resultList = BeanUtil.beanConvert(persistedList, SettingsDTO.class);

            if (resultList == null || resultList.size() == 0) {
                return new Response<List<SettingsDTO>>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return new Response<List<SettingsDTO>>(0, messages.getMessage("page.settings.message.save_success",
                    null,LocaleContextHolder.getLocale()), resultList);
        }catch(SettingsPersistenceException e) {
            throw new SettingsServiceException(e);
        }
    }




}
