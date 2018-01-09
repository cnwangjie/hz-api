package com.lf.hz.service;

import com.lf.hz.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ResourceService {

    @Autowired
    private Config config;

    public File getLocalFileByPath(String path) {
        Path resourcePath = Paths.get(config.getResoucesPath());
        File localFile = new File(Paths.get(resourcePath.toString(), path).toString());
        return localFile;
    }

    public String getURLByRelativePath(String relativePath) {
        String host = config.getHost();
        return host + '/' + Paths.get("resource", relativePath).toString();
    }
}
