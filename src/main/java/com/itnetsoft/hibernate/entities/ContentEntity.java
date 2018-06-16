package com.itnetsoft.hibernate.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "content")
public class ContentEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Lob
    @Column(name="title", columnDefinition = "text", nullable=false)
    private String title;

    @Lob
    @Column(name="seo_title", columnDefinition = "text")
    private String seoTitle;

    @Lob
    @Column(name="seo_keywords", columnDefinition = "text")
    private String seoKeywords;


    @Lob
    @Column(name="seo_description", columnDefinition = "text")
    private String seoDescription;

    @Lob
    @Column(name="excerpt",columnDefinition = "TEXT")
    private String excerpt;

    @Lob
    @Basic (fetch = FetchType.LAZY)
    @Column(name="content", columnDefinition = "longtext")
    private String content;

    @Column(name="parent", nullable=false,columnDefinition="INT default 0")
    private int parent = 0;

    @Column(name="status", nullable=false, columnDefinition="VARCHAR(20) default 'published'")
    private String status = "published";

    @Column(name="date_added", nullable = false, insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @Column(name="date_updated", updatable = true, insertable = true, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;


    private String url;

    @Column(name="content_type", nullable=false)
    private String contentType;

    @Column(name="menu_order")
    private int menuOrder = 0;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private UsersEntity author;

    @OneToMany(mappedBy = "contentId")
    private List<ContentBlocksEntity> contentBlock;

    @ManyToMany
    @JoinTable(name="relations", joinColumns = {
        @JoinColumn(name="content_id", referencedColumnName = "id")
    },
        inverseJoinColumns = {
            @JoinColumn(name = "taxonomy_id", referencedColumnName = "id")
    })
    private List<TaxonomyEntity> taxonomies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        if (dateUpdated == null) {
            dateUpdated = new Date();
        }
        this.dateUpdated = dateUpdated;
    }

    public UsersEntity getAuthor() {
        return author;
    }

    public void setAuthor(UsersEntity author) {
        this.author = author;
    }

    public List<ContentBlocksEntity> getContentBlock() {
        return contentBlock;
    }

    public void setContentBlock(List<ContentBlocksEntity> contentBlock) {
        this.contentBlock = contentBlock;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public List<TaxonomyEntity> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<TaxonomyEntity> taxonomies) {
        this.taxonomies = taxonomies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentEntity that = (ContentEntity) o;

        if (id != that.id) return false;
        if (parent != that.parent) return false;
        if (!author.equals(that.author)) return false;
        if (!dateAdded.equals(that.dateAdded)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + parent;
        result = 31 * result + dateAdded.hashCode();
        result = 31 * result + author.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ContentEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", seoTitle='" + seoTitle + '\'' +
                ", seoKeywords='" + seoKeywords + '\'' +
                ", seoDescription='" + seoDescription + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", url='" + url + '\'' +
                ", dateUpdated=" + dateUpdated +
                ", status='" + status + '\'' +
                ", parent=" + parent +
                '}';
    }
}
