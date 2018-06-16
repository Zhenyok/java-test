package com.itnetsoft.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "content_blocks")
public class ContentBlocksEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="key_field", unique = true)
    private String field;

    @Lob
    @Column(name="value",columnDefinition = "longtext", nullable = false)
    private String value;


    @ManyToOne
    @JoinColumn(name="content_id",referencedColumnName="id")
    private ContentEntity contentId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContentEntity getContentId() {
        return contentId;
    }

    public void setContentId(ContentEntity contentId) {
        this.contentId = contentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentBlocksEntity that = (ContentBlocksEntity) o;

        if (id != that.id) return false;
        if (!contentId.equals(that.contentId)) return false;
        if (!field.equals(that.field)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + field.hashCode();
        result = 31 * result + contentId.hashCode();
        return result;
    }
}
