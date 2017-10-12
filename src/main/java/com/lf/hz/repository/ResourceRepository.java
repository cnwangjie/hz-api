package com.lf.hz.repository;

import com.lf.hz.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Resource getOneByName(String name);
    Resource getOneByPath(String path);
}
