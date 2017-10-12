package com.lf.hz.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Resource {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT '文件名'")
    @NotNull
    private String name;

    @Column(columnDefinition = "varchar(255) COMMENT '文件路径'", unique = true)
    @NotNull
    private String path;

    @Column(columnDefinition = "varchar(255) COMMENT '资源跳转到的链接'")
    private String link;

    @Column(columnDefinition = "varchar(255) COMMENT '资源描述'")
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
