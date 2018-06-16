package com.itnetsoft.hibernate.entities;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "cat")
public class CatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="title", length=100)
    private String title;

    @Column(name="seo_title", length=100)
    private String seoTitle;

    @Column(name="seo_keywords")
    private String seoKeywords;

    @Column(name="seo_description", length=200)
    private String seoDescription;

    private String url;

    @OneToMany(mappedBy = "catId")
    private List<TaxonomyEntity> taxonomyList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TaxonomyEntity> getTaxonomyList() {
        return taxonomyList;
    }

    public void setTaxonomyList(List<TaxonomyEntity> taxonomyList) {
        this.taxonomyList = taxonomyList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }


    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }


    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatEntity catEntity = (CatEntity) o;

        if (id != catEntity.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
