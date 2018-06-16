package com.itnetsoft.services;


import com.itnetsoft.dal.IImageDAO;
import com.itnetsoft.dto.FileUploadDTO;
import com.itnetsoft.dto.ImageEditDTO;
import com.itnetsoft.dto.ImageInfoDTO;
import com.itnetsoft.dto.ImageManagerDTO;
import com.itnetsoft.exceptions.ImagePersistenceException;
import com.itnetsoft.exceptions.ImageServiceException;
import com.itnetsoft.exceptions.UserServiceException;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.UsersEntity;
import com.itnetsoft.jsonDTO.Response;
import com.itnetsoft.utils.BeanUtil;
import com.itnetsoft.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.List;

@Transactional
@Service
public class ImageServiceImpl implements  IImageService {

    @Value("${upload.absPath}")
    private String uploadDir;

    @Inject
    private MessageSource messages;

    @Inject
    private IImageDAO imageDAO;

    @Inject
    private IUserService userService;

    @Override
    public Response<List<ImageManagerDTO>> getManagerImageList() throws ImageServiceException {
        try {
            List<ImageManagerDTO> result = null;
            List<ContentEntity> daoList = null;
            daoList = imageDAO.getManagerImageList();
            if (daoList == null || daoList.size() == 0) {
                return new Response<List<ImageManagerDTO>>(0, "", null);
            }
            result = BeanUtil.beanConvert(daoList, ImageManagerDTO.class);
            return new Response<List<ImageManagerDTO>>(0, "", result);
        }catch(ImagePersistenceException e) {
            throw new ImageServiceException(e);
        }
    }

    @Override
    public Response<?> deleteImageItem(int id) throws ImageServiceException {
        if (id <= 0) {
            return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.delete_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
        try {
            boolean isDeleted = false;
            ContentEntity item = imageDAO.getItem(ContentEntity.class, id);
            if (item == null || !"IMAGE".equals(item.getContentType())) {
                return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.delete_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            if (!FileUtils.imageDelete(item.getUrl(), uploadDir)) {
                return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.delete_error",
                        null, LocaleContextHolder.getLocale()), null);
            }

            isDeleted =  imageDAO.deleteImageItem(id);
            if (isDeleted == false) {
                return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.delete_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return new Response<ImageManagerDTO>(0, messages.getMessage("page.settings.message.delete_success",
                    null, LocaleContextHolder.getLocale()), null);
        }catch(ImagePersistenceException  e) {
            throw new ImageServiceException(e);
        }
    }

    @Override
    public Response<ImageManagerDTO> saveImageItem(ImageManagerDTO item) throws ImageServiceException {
        try {
            ContentEntity saveConvertedItem = null;
            boolean isUpdated = false;
            UsersEntity user = null;
            saveConvertedItem = BeanUtil.beanConvert(item, ContentEntity.class);
            if (saveConvertedItem == null) {
                return new Response<ImageManagerDTO>(1, "", null);
            }
            user = getAuthority();
            if (user == null) {
                return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            saveConvertedItem.setAuthor(user);
            isUpdated = imageDAO.saveImageItem(saveConvertedItem);
            if (isUpdated == false) {
                return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return new Response<ImageManagerDTO>(0, messages.getMessage("page.settings.message.save_success",
                    null, LocaleContextHolder.getLocale()), null);
        }catch(ImagePersistenceException | UserServiceException e) {
            throw new ImageServiceException(e);
        }
    }

    private UsersEntity getAuthority() throws UserServiceException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsersEntity user = null;
        user = userService.getUserByName(auth.getName());
        return user;
    }

    @Override
    public Response<List<ImageManagerDTO>> uploadImageFiles(List<MultipartFile> files) throws ImageServiceException {
        List<FileUploadDTO> uploadFiles = null;
        uploadFiles = BeanUtil.beanConvert(files);

        if (!FileUtils.makeDirsAndUpload(uploadFiles, uploadDir)) {
            return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.upload_error",
                    null, LocaleContextHolder.getLocale()), null);
        }

        List<ContentEntity> result = null;
        result = BeanUtil.beanConvert(uploadFiles, ContentEntity.class);

        try {
            UsersEntity user = getAuthority();
            if (user == null) {
                return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.upload_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            result = imageDAO.uploadImageFiles(result, user);
            List<ImageManagerDTO> uploadedFiles = BeanUtil.beanConvert(result, ImageManagerDTO.class);

            if (uploadedFiles == null || uploadedFiles.size() != result.size()) {
                return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.upload_error",
                        null, LocaleContextHolder.getLocale()), null);
            }

            return new Response<List<ImageManagerDTO>>(0, messages.getMessage("page.settings.message.upload_success",
                    null, LocaleContextHolder.getLocale()), uploadedFiles);
        } catch (ImagePersistenceException | UserServiceException e){
            throw new ImageServiceException(e);
        }

    }

    @Override
    public Response<ImageInfoDTO> processImageDimensions(ImageEditDTO image) throws ImageServiceException {
        Response<ImageInfoDTO> result = null;
        try {
            ContentEntity item = null;
            ImageInfoDTO imageResult = null;
            item = imageDAO.getItem(ContentEntity.class, image.getId());
            imageResult = BeanUtil.beanConvert(item, ImageInfoDTO.class);
            if (imageResult == null) {
                return new Response<ImageInfoDTO>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            if (!FileUtils.changeImageSize(image, imageResult, uploadDir)) {
                return new Response<ImageInfoDTO>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }

            return new Response<ImageInfoDTO>(0, "page.settings.message.save_success", imageResult);
        } catch(ImagePersistenceException e){
            throw new ImageServiceException(e);
        }
    }

    @Override
    public Response<ImageInfoDTO> getImageItem(int id) throws ImageServiceException {
        Response<ImageInfoDTO> result = null;
        try {
            ContentEntity item = null;
            ImageInfoDTO image = null;
            item = imageDAO.getItem(ContentEntity.class,id);
            image = BeanUtil.beanConvert(item, ImageInfoDTO.class);
            if (image == null) {
                return new Response<ImageInfoDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            if (!FileUtils.setImageDimensions(image, uploadDir)) {
                return new Response<ImageInfoDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return new Response<ImageInfoDTO>(0, "", image);
        } catch(ImagePersistenceException e){
            throw new ImageServiceException(e);
        }
    }
}
