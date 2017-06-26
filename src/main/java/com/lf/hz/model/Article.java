package com.lf.hz.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Entity
public class Article {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(255)")
    private String title;

    @Column(columnDefinition = "text", nullable = true)
    private String content;

    @Column(columnDefinition = "varchar(100) COMMENT '显示的创建人'")
    private String author;

    @ManyToMany(mappedBy = "articles")
    private Set<Cate> cates;

    @ManyToMany(mappedBy = "articles")
    private Set<Cate> tags;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
