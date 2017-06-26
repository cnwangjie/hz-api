package com.lf.hz.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cate {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(100) COMMENT '分类名'")
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="article_cate",
            joinColumns = {@JoinColumn(name = "cate_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "article_id", referencedColumnName = "id")})
    private Set<Article> articles = new HashSet<Article>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
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
