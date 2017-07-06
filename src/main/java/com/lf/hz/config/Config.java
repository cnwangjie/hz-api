package com.lf.hz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("application")
public class Config {

    @Value("${config.debug}")
    private Boolean debug;

    @Value("${config.resouces.path}")
    private String resoucesPath;

    @Value("${config.jwt.header}")
    private String jwtHeader;

    @Value("${config.jwt.secret}")
    private String jwtSecret;

    public Boolean getDebug() {
        return debug;
    }

    public String getResoucesPath() {
        return resoucesPath;
    }

    public String getJwtHeader() {
        return jwtHeader;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }
}
