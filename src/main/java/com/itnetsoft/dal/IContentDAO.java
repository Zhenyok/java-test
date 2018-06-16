package com.itnetsoft.dal;


import com.itnetsoft.exceptions.ContentPersistenceException;
import com.itnetsoft.hibernate.entities.TestimonialsEntity;

import java.util.List;

public interface IContentDAO {
    public TestimonialsEntity saveTestimonial(TestimonialsEntity item) throws ContentPersistenceException;
    public List<TestimonialsEntity> getTestimonialList() throws ContentPersistenceException;
    public boolean delTestimonial(int id) throws ContentPersistenceException;
    public boolean delTestimonial(List<TestimonialsEntity> entityList) throws ContentPersistenceException;

}
