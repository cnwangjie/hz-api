package com.lf.hz.repository;

import com.lf.hz.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List findByIpAndUa(String ip, String ua);
}
