package com.lf.hz.http.api;

import com.lf.hz.repository.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Inject
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @RequestMapping(value = "/lastest", method = RequestMethod.GET)
    public ResponseEntity index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List articles = articleRepository.findAll(new PageRequest(page, size,
                new Sort(Sort.Direction.DESC, "createdAt"))).getContent();
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Integer id) {
        return new ResponseEntity(articleRepository.findOneById(id), HttpStatus.OK);
    }
}
