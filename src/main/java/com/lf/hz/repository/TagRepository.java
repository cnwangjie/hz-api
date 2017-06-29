package com.lf.hz.repository;

import com.lf.hz.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag getOneById(Integer io);
}
