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

/**
 * @apiDefine article 文章
 */

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private LogRepository logRepository;

    /**
     * @api {get} /api/article/lastest 获取最新的文章列表
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {Number} [page=0] 页码
     * @apiParam {Number} [size=10] 每页容量
     * @apiSuccess {Object[]} articles 文章列表
     * @apiSuccess {Number}    articles.id         id
     * @apiSuccess {String}    articles.title      标题
     * @apiSuccess {String}    articles.content    内容 (html)
     * @apiSuccess {String}    articles.author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  articles.cates      分类
     * @apiSuccess {Object[]}  articles.tags       标签
     * @apiSuccess {Number}    articles.createdAt  创建时间的时间戳
     * @apiSuccess {Number}    articles.updatedAt  修改时间的时间戳
     */
    @RequestMapping(value = "/lastest", method = RequestMethod.GET)
    public ResponseEntity index(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List articles = articleRepository.findAll(new PageRequest(page, size,
                new Sort(Sort.Direction.DESC, "createdAt"))).getContent();
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    /**
     * @api {get} /api/article/:id 获取单个文章详情
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {Number} id id
     * @apiSuccess {Number}    id         id
     * @apiSuccess {String}    title      标题
     * @apiSuccess {String}    content    内容 (html)
     * @apiSuccess {String}    author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  cates      分类
     * @apiSuccess {Object[]}  tags       标签
     * @apiSuccess {Number}    createdAt  创建时间的时间戳
     * @apiSuccess {Number}    updatedAt  修改时间的时间戳
     *
     * @apiError (404) {String} status 状态
     * @apiError (404) {String} msg 错误信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Integer id) {
        Article article = articleRepository.findOneById(id);
        if (article != null) {
            return new ResponseEntity(article, HttpStatus.OK);
        } else {
            Map json = new HashMap();
            json.put("status", "error");
            json.put("msg", "not found");
            return new ResponseEntity(json, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @api {get} /api/article/search 搜索文章
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {String} word 关键词
     * @apiSuccess {Object[]} articles 文章列表
     * @apiSuccess {Number}    articles.id         id
     * @apiSuccess {String}    articles.title      标题
     * @apiSuccess {String}    articles.content    内容 (html)
     * @apiSuccess {String}    articles.author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  articles.cates      分类
     * @apiSuccess {Object[]}  articles.tags       标签
     * @apiSuccess {Number}    articles.createdAt  创建时间的时间戳
     * @apiSuccess {Number}    articles.updatedAt  修改时间的时间戳
     *
     * @apiErrorExample 参数错误
     *      HTTP/1.1 400 Bad Request
     *
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "word", defaultValue = "") String word) {
        if (word == "") {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(articleRepository.findByContentContaining(word), HttpStatus.OK);
    }

    /**
     * @api {post} /api/article/add 添加文章
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {String} word 关键词
     * @apiSuccess {Number}    id         id
     * @apiSuccess {String}    title      标题
     * @apiSuccess {String}    content    内容 (html)
     * @apiSuccess {String}    author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  cates      分类
     * @apiSuccess {Object[]}  tags       标签
     * @apiSuccess {Number}    createdAt  创建时间的时间戳
     * @apiSuccess {Number}    updatedAt  修改时间的时间戳
     */
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

    /**
     * @api {post} /api/article/:id/delete 删除文章
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {Number} id id
     * @apiSuccessExample 成功
     *      HTTP/1.1 200 OK
     *
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        articleRepository.delete((long) id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @api {get} /api/article/recently 最近浏览的文章
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiHeader (default) {String} User-Agent ua
     *
     * @apiParam  (param)   {String} ip ip
     * @apiParam  (param)   {String} ua User Agent
     *
     * @apiSuccess {Object[]}  articles 文章列表
     * @apiSuccess {Number}    articles.id         id
     * @apiSuccess {String}    articles.title      标题
     * @apiSuccess {String}    articles.content    内容 (html)
     * @apiSuccess {String}    articles.author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  articles.cates      分类
     * @apiSuccess {Object[]}  articles.tags       标签
     * @apiSuccess {Number}    articles.createdAt  创建时间的时间戳
     * @apiSuccess {Number}    articles.updatedAt  修改时间的时间戳
     *
     * @apiErrorExample 参数错误
     *      HTTP/1.1 400 Bad Request
     *
     */
    @RequestMapping(value = "/recently", method = RequestMethod.GET)
    public ResponseEntity recentlyReaded(HttpServletRequest req,
                                         @RequestParam(value = "ip", required = false) String ip,
                                         @RequestParam(value = "ua", required = false) String ua) {
        if (ip.isEmpty()) ip = req.getRemoteHost();
        if (ua.isEmpty()) ua = req.getHeader("User-Agent");

        if (ua.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List logs = logRepository.findByIpAndUa(ip, ua);
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

    /**
     * @api {get} /api/article/random 随机文章
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {Number} [sum=5] 文章数量
     *
     * @apiSuccess {Object[]}  articles 文章列表
     * @apiSuccess {Number}    articles.id         id
     * @apiSuccess {String}    articles.title      标题
     * @apiSuccess {String}    articles.content    内容 (html)
     * @apiSuccess {String}    articles.author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  articles.cates      分类
     * @apiSuccess {Object[]}  articles.tags       标签
     * @apiSuccess {Number}    articles.createdAt  创建时间的时间戳
     * @apiSuccess {Number}    articles.updatedAt  修改时间的时间戳
     *
     */
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