package com.lf.hz.http.api;

import com.lf.hz.repository.NavRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nav")
public class NavController {

    @Autowired
    private NavRepository navRepository;

    @RequestMapping("")
    public ResponseEntity index() {
        return new ResponseEntity(navRepository.findAll(), HttpStatus.OK);
    }
}
