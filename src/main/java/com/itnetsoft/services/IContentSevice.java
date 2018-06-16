package com.itnetsoft.services;


import com.itnetsoft.dto.TestimonialDTO;
import com.itnetsoft.exceptions.ContentServiceException;
import com.itnetsoft.jsonDTO.Response;

import java.util.List;

public interface IContentSevice {

    public Response<TestimonialDTO> saveTestimonial(TestimonialDTO testimonial) throws ContentServiceException;
    public Response<List<TestimonialDTO>> getTestimonialList() throws ContentServiceException;
    public Response<TestimonialDTO> delTestimonial(int id) throws ContentServiceException;
    public Response<TestimonialDTO> delTestimonial(List<TestimonialDTO> tmnlList) throws ContentServiceException;

}
