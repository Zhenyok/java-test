package com.itnetsoft.controllers;

import com.itnetsoft.dto.*;
import com.itnetsoft.exceptions.*;
import com.itnetsoft.hibernate.entities.ContentEntity;
import com.itnetsoft.hibernate.entities.UsersEntity;
import com.itnetsoft.jsonDTO.Response;
import com.itnetsoft.jsonDTO.ResponseHttpError;
import com.itnetsoft.services.IContentSevice;
import com.itnetsoft.services.IImageService;
import com.itnetsoft.services.ISettingsService;
import com.itnetsoft.services.IUserService;
import com.itnetsoft.utils.BeanUtil;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private static final Logger log = LogManager.getLogger(DashboardController.class);

    @Inject
    private ISettingsService settingsService;

    @Inject
    private IImageService imageService;

    @Inject
    private MessageSource messages;

    @Inject
    private IContentSevice contentService;

    @RequestMapping("/")
    public String index(Model model) {
        return "dashboard-index";
    }

    @RequestMapping("")
    public String indexDash(Model model) {
        return "dashboard-index";
    }

    @RequestMapping(value = "/settings", method=RequestMethod.GET)
    public String getSettingsTemplate() {
        return "ngview/settings";
    }

    @RequestMapping(value="/editMedia", method=RequestMethod.GET)
    public String getEditMediaTemplate(){
        return "ngview/editMedia";
    }

    @RequestMapping(value = "/pageAdd", method=RequestMethod.GET)
    public String addPageTemplate() {
        return "ngview/pageAdd";
    }

    @RequestMapping(value = "/testimnl", method=RequestMethod.GET)
    public String getTmnlsView() {
        return "ngview/testimnl";
    }

    @RequestMapping(value = "/addTestimnl", method=RequestMethod.GET)
    public String addTmnlsView() {
        return "ngview/addTestimnl";
    }

    @RequestMapping(value = "/getImageInfo/{id}", method = RequestMethod.GET)
    public @ResponseBody  Response<ImageInfoDTO> getImageInfo(Model model, @PathVariable int id) {
        Response<ImageInfoDTO> info = null;
        try {
            info = imageService.getImageItem(id);
            return info;
        } catch(ImageServiceException e) {
            log.error(e.getMessage(), e);
            return new Response<ImageInfoDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }


    @RequestMapping(value = "/articles", method=RequestMethod.GET)
    public String getArticlesTemplate() {
        return "ngview/articles";
    }

    @RequestMapping(value = "/getSettingsParams", method = RequestMethod.GET)
    public @ResponseBody Response<List<SettingsDTO>> getSettings() {
        Response<List<SettingsDTO>> response = null;
        try {
            response = settingsService.getSettingsList();
            return response;
        } catch(SettingsServiceException e) {
            log.error(e.getMessage(), e);
           return new Response<List<SettingsDTO>>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }

    }

    @RequestMapping(value = "/deleteItem/{id}", method = RequestMethod.GET)
    public @ResponseBody  Response<SettingsDTO> deleteSetting(Model model, @PathVariable int id) {
        Response<SettingsDTO> response = null;
        try {
            response = settingsService.deleteSetting(id);
            return response;
        } catch(SettingsServiceException e) {
            log.error(e.getMessage(), e);
            return new Response<SettingsDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/addSetting", method = RequestMethod.POST)
    public @ResponseBody  Response<SettingsDTO> addSettingItem(Model model, @RequestBody SettingsDTO setting) {
        Response<SettingsDTO> response = null;
        try {
            response = settingsService.addSettingItem(setting);
            return response;
        } catch(SettingsServiceException e) {
            log.error(e.getMessage(), e);
            return new Response<SettingsDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/saveChanges", method = RequestMethod.POST)
    public @ResponseBody  Response<List<SettingsDTO>> applySettings(Model model,
                                                                    @RequestBody List<SettingsDTO> setting) {

        Response<List<SettingsDTO>> response = null;
        try {
            response = settingsService.saveSettingsList(setting);
            return response;
        } catch(SettingsServiceException e) {
            log.error(e.getMessage(), e);
            return new Response<List<SettingsDTO>>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/getManagerImageList", method = RequestMethod.GET)
    public @ResponseBody  Response<List<ImageManagerDTO>> getManagerImageList(Model model) {
        Response<List<ImageManagerDTO>> response = null;
        try {
            response = imageService.getManagerImageList();
            return response;
        } catch(ImageServiceException e) {
            log.error(e.getMessage(), e);
            return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/deleteManagerImage/{id}", method = RequestMethod.GET)
    public @ResponseBody  Response<?> deleteManagerImage(Model model, @PathVariable int id) {
        Response<List<ImageManagerDTO>> response = null;
        try {
            Response<?> deleted = null;
            deleted = imageService.deleteImageItem(id);
            if (deleted == null) {
                return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.delete_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return deleted;
        } catch(ImageServiceException e) {
            log.error(e.getMessage(), e);
            return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/saveImageItem", method = RequestMethod.POST)
    public @ResponseBody  Response<ImageManagerDTO> setImageItemUpdate(@RequestBody ImageManagerDTO imageItem) {
        Response<ImageManagerDTO> response = null;
        try {
            response = imageService.saveImageItem(imageItem);
            return response;
        } catch(ImageServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<ImageManagerDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/imageUploadSet", method = RequestMethod.POST,  headers = "content-type=multipart/form-data")
    public @ResponseBody  Response<List<ImageManagerDTO>> uploadImageFiles(@RequestParam("file") final List<MultipartFile> files) {
        try {
            Response<List<ImageManagerDTO>> response = null;
            response = imageService.uploadImageFiles(files);
            return response;
        } catch(ImageServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<List<ImageManagerDTO>>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/CropImage", method = RequestMethod.POST)
    public @ResponseBody  Response<ImageInfoDTO> cropImage(@RequestBody ImageEditDTO cropImageInfo) {
        try {
            Response<ImageInfoDTO> response = null;
            response = imageService.processImageDimensions(cropImageInfo);
            return response;
        } catch(ImageServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<ImageInfoDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/saveTmnl", method = RequestMethod.POST)
    public @ResponseBody  Response<TestimonialDTO> saveTestimonial(@RequestBody TestimonialDTO tmnl) {
        try {
            Response<TestimonialDTO> response = null;
            response = contentService.saveTestimonial(tmnl);
            return response;
        } catch(ContentServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/getTmnls", method = RequestMethod.GET)
    public @ResponseBody  Response<List<TestimonialDTO>> getTestimonialList() {
        try {
            Response<List<TestimonialDTO>> response = null;
            response = contentService.getTestimonialList();
            return response;
        } catch(ContentServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<List<TestimonialDTO>>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/delTmnls", method = RequestMethod.POST)
    public @ResponseBody  Response<TestimonialDTO> deleteTestimonialList(@RequestBody List<TestimonialDTO> tmnlsList) {
        try {
            Response<TestimonialDTO> response = null;
            response = contentService.delTestimonial(tmnlsList);
            return response;
        } catch(ContentServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }

    @RequestMapping(value = "/delTmnls/{id}", method = RequestMethod.GET)
         public @ResponseBody  Response<TestimonialDTO> deleteTestimonial(@PathVariable int id) {
        try {
            Response<TestimonialDTO> response = null;
            response = contentService.delTestimonial(id);
            return response;
        } catch(ContentServiceException  e) {
            log.error(e.getMessage(), e);
            return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.unknown_error",
                    null, LocaleContextHolder.getLocale()), null);
        }
    }


    @ExceptionHandler({AuthenticationException.class})
    public void HandleAuthorizationError(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException ex) throws IOException {
        if( "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.sendError(response.SC_UNAUTHORIZED, "Unauthorized access");
        } else {
            response.sendRedirect("/login");
        }
    }

    @ExceptionHandler({SettingsServiceException.class, UserServiceException.class,ImageServiceException.class,
            ContentServiceException.class})
    public @ResponseBody ResponseHttpError HandleServiceException(HttpServletRequest request, Exception ex){
        log.error(ex.getMessage(), ex);
        return new ResponseHttpError(1,  messages.getMessage("page.settings.message.service_error",
                null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler({SettingsPersistenceException.class, UserPersistenceException.class, ImagePersistenceException.class,
            ContentPersistenceException.class})
    public @ResponseBody ResponseHttpError HandlePersistenceException(HttpServletRequest request, Exception ex){
        log.error(ex.getMessage(), ex);
        return new ResponseHttpError(1,  messages.getMessage("page.settings.message.database_error",
                null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler({Exception.class})
    public @ResponseBody ResponseHttpError HandleErrorException(HttpServletRequest request, Exception ex){
        log.error(ex.getMessage(), ex);
        return new ResponseHttpError(1, messages.getMessage("page.settings.message.watch",
                null, LocaleContextHolder.getLocale()));
    }

}
