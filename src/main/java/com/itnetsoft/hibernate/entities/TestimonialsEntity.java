package com.itnetsoft.hibernate.entities;

import javax.persistence.*;


@Entity
@Table(name = "testimonials")
public class TestimonialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    @Column(name="testimonial",nullable = false, columnDefinition = "text")
    private String testimonial;

    @Lob
    @Column(name="author", columnDefinition = "text",  nullable = false)
    private String author;

    @Lob
    @Column(name="job_title", columnDefinition = "text", nullable=false)
    private String jobTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestimonial() {
        return testimonial;
    }

    public void setTestimonial(String testimonial) {
        this.testimonial = testimonial;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestimonialsEntity that = (TestimonialsEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
