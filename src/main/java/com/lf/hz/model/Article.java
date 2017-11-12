package com.lf.hz.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(255)")
    private String image;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @NotNull
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    @NotNull
    private String content;

    @Column(columnDefinition = "varchar(100) COMMENT '显示的创建人'", nullable = true)
    private String author;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="article_cate",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "cate_id")})
    private Set<Cate> cates = new HashSet<Cate>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="article_tag",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @Column(name = "created_at", nullable = true)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Cate> getCates() {
        return cates;
    }

    public void setCates(Set<Cate> cates) {
        this.cates = cates;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
