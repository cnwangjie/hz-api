package com.lf.hz.http.api;

import com.lf.hz.config.Config;
import com.lf.hz.model.Resource;
import com.lf.hz.repository.ResourceRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private Config config;

    private final ResourceRepository resourceRepository;

    @Inject
    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity resource(@PathVariable String filename) throws IOException{
        Resource resource = resourceRepository.getOneByName(filename);
        if (resource == null) {
            return new ResponseEntity(filename + " cannot be found", HttpStatus.NOT_FOUND);
        }

        File file = new File(config.getResoucesPath()+ resource.getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(resource.getType()));
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }
}
