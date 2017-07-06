package com.lf.hz.http.api;

import com.lf.hz.model.Article;
import com.lf.hz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "word", defaultValue = "") String word) {
        if (word == "") {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(articleRepository.findByContentContaining(word), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity add(@RequestParam(value = "title", required = true) String title,
                              @RequestParam(value = "content", required = true) String content,
                              @RequestParam(value = "author", required = false) String author) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(author);
        try {
            articleRepository.save(article);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(article, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        articleRepository.delete((long) id);
        return new ResponseEntity(HttpStatus.OK);
    }
}