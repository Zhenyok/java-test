package com.itnetsoft.hibernate.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "taxonomy")
public class TaxonomyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "parent", nullable = false)
    private int parent = 0;

    @Column(name="taxonomy_type", length = 100)
    private String taxonomyType;

    @Lob
    @Basic (fetch = FetchType.LAZY)
    @Column(name="description", columnDefinition = "longtext")
    private String description;

    private int count = 0;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CatEntity catId;

    @ManyToMany(mappedBy = "taxonomies")
    private List<ContentEntity> contentList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }


    public String getTaxonomyType() {
        return taxonomyType;
    }

    public void setTaxonomyType(String taxonomyType) {
        this.taxonomyType = taxonomyType;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public CatEntity getCatId() {
        return catId;
    }

    public void setCatId(CatEntity catId) {
        this.catId = catId;
    }

    public List<ContentEntity> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentEntity> contentList) {
        this.contentList = contentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxonomyEntity that = (TaxonomyEntity) o;

        if (id != that.id) return false;
        if (parent != that.parent) return false;
        if (!catId.equals(that.catId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + parent;
        result = 31 * result + catId.hashCode();
        return result;
    }
}
