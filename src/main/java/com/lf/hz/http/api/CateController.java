package com.lf.hz.http.api;

import com.lf.hz.model.Cate;
import com.lf.hz.repository.CateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cate")
public class CateController {

    @Autowired
    private CateRepository cateRepository;

    @RequestMapping("/all")
    public ResponseEntity all() {
        return new ResponseEntity(cateRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity get(@PathVariable Integer id) {
        Cate cate = cateRepository.getOneById(id);
        if (cate == null) {
            Map json = new HashMap();
            json.put("status", "error");
            json.put("error", "CateError");
            json.put("msg", "this cate is not exist");
            return new ResponseEntity(json, HttpStatus.OK);
        } else {
            return new ResponseEntity(cate.getArticles(), HttpStatus.OK);
        }
    }
}
