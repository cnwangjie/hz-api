package com.lf.hz.http.api;

import com.lf.hz.repository.NavRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/nav")
public class NavController {

    private final NavRepository navRepository;

    @Inject
    public NavController(NavRepository navRepository) {
        this.navRepository = navRepository;
    }

    @RequestMapping("")
    public ResponseEntity index() {
        return new ResponseEntity(navRepository.findAll(), HttpStatus.OK);
    }
}
