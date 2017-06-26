package com.lf.hz.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Page {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(255) COMMENT '页面的标题'")
    private String title;

    @Column(columnDefinition = "text COMMENT '页面的内容(容器中的html代码)'")
    private String content;

    @Column(columnDefinition = "varchar(255) COMMENT '页面在路由当中的路径'")
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
