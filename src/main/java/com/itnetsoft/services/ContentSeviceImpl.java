package com.itnetsoft.services;

import com.itnetsoft.dal.IContentDAO;
import com.itnetsoft.dto.TestimonialDTO;
import com.itnetsoft.exceptions.ContentPersistenceException;
import com.itnetsoft.exceptions.ContentServiceException;
import com.itnetsoft.hibernate.entities.TestimonialsEntity;
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
public class ContentSeviceImpl implements IContentSevice {

    @Inject
    private IContentDAO contentDAO;

    @Inject
    private MessageSource messages;

    @Override
    public Response<TestimonialDTO> saveTestimonial(TestimonialDTO testimonial) throws ContentServiceException {
        try {
            TestimonialsEntity tmnl = null;
            TestimonialDTO result = null;
            tmnl = BeanUtil.beanConvert(testimonial, TestimonialsEntity.class);
            tmnl = contentDAO.saveTestimonial(tmnl);
            if (tmnl == null || tmnl.getId() == 0) {
                return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            result = BeanUtil.beanConvert(tmnl, TestimonialDTO.class);

            if (result == null) {
                return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.save_error",
                        null, LocaleContextHolder.getLocale()), null);
            }

            return new Response<TestimonialDTO>(0, messages.getMessage("page.settings.message.save_success",
                    null, LocaleContextHolder.getLocale()), result);

        } catch(ContentPersistenceException e){
            throw new ContentServiceException(e);
        }

    }

    @Override
    public Response<List<TestimonialDTO>> getTestimonialList() throws ContentServiceException {
        try {
            List<TestimonialDTO> result = null;
            List<TestimonialsEntity> entity = null;
            entity = contentDAO.getTestimonialList();
            result = BeanUtil.beanConvert(entity, TestimonialDTO.class);
            return new Response<List<TestimonialDTO>>(0, "", result);
        } catch(ContentPersistenceException e){
            throw new ContentServiceException(e);
        }
    }

    @Override
    public Response<TestimonialDTO> delTestimonial(int id) throws ContentServiceException {
        try {
            boolean deleted = false;
            deleted = contentDAO.delTestimonial(id);
            if (!deleted) {
                return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.delete_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return new Response<TestimonialDTO>(0, messages.getMessage("page.settings.message.delete_success",
                    null, LocaleContextHolder.getLocale()), null);
        } catch(ContentPersistenceException e){
            throw new ContentServiceException(e);
        }
    }

    @Override
    public Response<TestimonialDTO> delTestimonial(List<TestimonialDTO> tmnlList) throws ContentServiceException {
        try {
            boolean deleted = false;
            List<TestimonialsEntity> entityList = null;
            entityList = BeanUtil.beanConvert(tmnlList, TestimonialsEntity.class);
            deleted = contentDAO.delTestimonial(entityList);
            if (!deleted) {
                return new Response<TestimonialDTO>(1, messages.getMessage("page.settings.message.delete_error",
                        null, LocaleContextHolder.getLocale()), null);
            }
            return new Response<TestimonialDTO>(0, messages.getMessage("page.settings.message.delete_success",
                    null, LocaleContextHolder.getLocale()), null);
        } catch(ContentPersistenceException e){
            throw new ContentServiceException(e);
        }
    }
}
