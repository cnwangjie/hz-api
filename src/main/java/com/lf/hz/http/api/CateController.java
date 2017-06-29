package com.lf.hz.http.api;

import com.lf.hz.repository.CateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/cate")
public class CateController {

    private final CateRepository cateRepository;

    @Inject
    public CateController(CateRepository cateRepository) {
        this.cateRepository = cateRepository;
    }

    @RequestMapping("/all")
    public ResponseEntity all() {
        return new ResponseEntity(cateRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity get(@PathVariable Integer id) {
        return new ResponseEntity(cateRepository.getOneById(id).getArticles(), HttpStatus.OK);
    }
}
