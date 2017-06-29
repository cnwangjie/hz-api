package com.lf.hz.repository;

import com.lf.hz.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findOneById(Integer id);

}