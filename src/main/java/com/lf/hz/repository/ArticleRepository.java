package com.lf.hz.repository;

import com.lf.hz.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {
    void deleteById(Integer id);
    Article findOneById(Integer id);

    List findByContentContaining(String word0);
}
