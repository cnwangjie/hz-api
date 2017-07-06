package com.lf.hz.repository;

import com.lf.hz.model.Authority;
import com.lf.hz.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority getOneByName(AuthorityName name);
}
