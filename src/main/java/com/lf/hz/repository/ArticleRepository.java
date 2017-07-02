package com.lf.hz.repository;

import com.lf.hz.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findOneById(Integer id);

    List findByContentContaining(String word0);
}
