package com.lf.hz.repository;

import com.lf.hz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getOneByUsername(String username);
}
