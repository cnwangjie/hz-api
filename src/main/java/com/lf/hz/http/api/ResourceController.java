package com.lf.hz.http.api;

import com.lf.hz.model.Resource;
import com.lf.hz.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.File;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    private final ResourceRepository resourceRepository;

    @Value("${config.resouces-path}")
    private String resourcePath;

    @Inject
    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
    @RequestMapping("/{filename}")
    public ResponseEntity resource(@PathVariable String filename) {
        Resource resource = resourceRepository.getOneByName(filename);
        if (resource == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        File file = new File(resourcePath + resource.getUuid());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(resource.getType()));
        return new ResponseEntity(file, headers, HttpStatus.OK);
    }
}
