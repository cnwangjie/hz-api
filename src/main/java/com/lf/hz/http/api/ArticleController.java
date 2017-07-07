package com.lf.hz.http.api;

import com.lf.hz.model.Article;
import com.lf.hz.model.Log;
import com.lf.hz.repository.ArticleRepository;
import com.lf.hz.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private LogRepository logRepository;

    @RequestMapping(value = "/lastest", method = RequestMethod.GET)
    public ResponseEntity index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List articles = articleRepository.findAll(new PageRequest(page, size,
                new Sort(Sort.Direction.DESC, "createdAt"))).getContent();
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Integer id) {
        Article article = articleRepository.findOneById(id);
        if (article != null) {
            return new ResponseEntity(article, HttpStatus.OK);
        } else {
            Map json = new HashMap();
            json.put("status", "error");
            json.put("msg", "not found");
            return new ResponseEntity(json, HttpStatus.OK);
        }
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

    @RequestMapping(value = "/recently", method = RequestMethod.GET)
    public ResponseEntity recentlyReaded(HttpServletRequest req) {
        List logs = logRepository.findByIpAndUa(req.getRemoteHost(), req.getHeader("User-Agent"));
        logs.sort(new Comparator<Log>() {
            @Override
            public int compare(Log o, Log t1) {
                return o.getId() - t1.getId();
            }
        });
        Set<Article> result = new HashSet<>();

        for (int i = 0; i < logs.size() && result.size() < 5; i += 1) {
            Log log = (Log) logs.get(i);
            String page = log.getPage();
            Pattern r = Pattern.compile("article/(\\d+)");
            Matcher m = r.matcher(page);
            if (m.find()) {
                Article a = articleRepository.findOneById(Integer.parseInt(m.group(1)));
                if (a != null) {
                    result.add(a);
                }
            }
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/random")
    public ResponseEntity random(@RequestParam(value = "sum", defaultValue = "5") Integer sum) {
        Set<Article> articles = new HashSet<>();
        int articleCount = (int) articleRepository.count();
        int count = 0;
        while (articles.size() < sum && count < 100) {
            count += 1;
            int randId = (int) (Math.random() * articleCount);
            Article randArticle = articleRepository.findOneById(randId);
            if (randArticle != null) {
                articles.add(randArticle);
            }
        }

        return new ResponseEntity(articles, HttpStatus.OK);
    }
}