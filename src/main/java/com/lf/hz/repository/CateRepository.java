package com.lf.hz.repository;

import com.lf.hz.model.Cate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateRepository extends JpaRepository<Cate, Long> {
    Cate getOneById(Integer id);
    Cate getOneByName(String name);
}
