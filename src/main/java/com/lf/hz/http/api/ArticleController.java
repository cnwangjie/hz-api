package com.lf.hz.http.api;

import com.lf.hz.model.Article;
import com.lf.hz.model.Cate;
import com.lf.hz.model.Log;
import com.lf.hz.repository.ArticleRepository;
import com.lf.hz.repository.CateRepository;
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

    /**
     * @apiDefine multiarticle
     * @apiSuccessExample {json} 200
     * [
    {
    "id": 1,
    "title": "徽州每个宗族都有族谱 几百万年从未中断",
    "content": "\n<div id=\"artical_sth\">\n<p>\n<span>2013年06月29日 13:21</span><br/> \t\t\t\t\t\t来源：凤凰卫视\t\t\t\t\t</p>\n<div id=\"fa_share\">\n<table>\n<tbody><tr>\n<td><span>分享到：</span></td>\n<td><div id=\"bdshare\">\n<a target=\"_blank\"></a>\n<a target=\"_blank\"></a>\n<a target=\"_blank\"></a>\n<span>更多</span>\n</div></td>\n</tr>\n</tbody></table>\n\n\n\n<div></div>\n<div id=\"artical_real\">\n<div>\n<div><div id=\"VideoPlayerDiv\">&quot;正在加载中...&quot;<br/></div>\n\n<div id=\"main_content\">\r\n\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t徽州每个宗族都有族谱 几百万年从未中断\t\t\t\t\t\r\n            </div>\n\n<div id=\"padhide_1952\">\n</div>\n<div>\n</div>\n<div></div> <div id=\"artical_sth2\">\n<span></span>                    标签：<a href=\"http://search.ifeng.com/sofeng/search.action?c=1&amp;q=%E5%BE%BD%E5%B7%9E%20%E5%AE%97%E6%97%8F%20%E6%97%8F%E8%B0%B1\" target=\"_blank\">徽州 宗族 族谱</a> </div>\n\n<div id=\"artical_line\"> </div>\n<div></div>\n<div>\n</div>\n\n<div></div>\n<div></div>\n\n<div></div>\n<div><a href=\"http://news.ifeng.com/history\" target=\"_blank\">返回凤凰网历史首页</a></div>\n</div>",
    "author": null,
    "cates": [],
    "tags": [],
    "createdAt": null,
    "updatedAt": null
    },
    {
    "id": 2,
    "title": "徽州土地关系",
    "content": "<div id=\"content\">\n<div>\n<h2>\n<a name=\"1\" target=\"_blank\"> </a>内容介绍<span>/徽州土地关系 </span><a href=\"http://www.baike.com/wiki/%E5%BE%BD%E5%B7%9E%E5%9C%9F%E5%9C%B0%E5%85%B3%E7%B3%BB\" target=\"_blank\">编辑</a>\n</h2>\n</div>\n<p></p>这批史料内容丰富，涉及经济史、社会史、文化史等诸多方面，而尤以经济史资料数量为多。其中土地买卖契约、土地租佃契约、置产簿、收租簿、分家书、黄册、鱼鳞图册、赋役文书、私家账册，以及有关土地诉讼案卷等，都是研究我国封建社会晚期土地关系史难得的第一手资料。<br/>土地关系史在于考察各个时代的生产及其制度机制和运行、生产关系形态及其变迁、生产者劳动和生活状况等。徽州虽然只是全国的一隅，但并非孤立，它和全国的政治、经济、文化有着密切的联系。我国历史上地方文书契约能够比较完整地保存下来的地区不多，其所反映的有关土地关系情况，既有特殊性，亦具有普遍性，特别是在我国东南地区，更具有一定的典型意义。这一研究的进展，不仅有助于揭示明清时期徽州土地关系的真实面貌，同时也有助于对同一时期相邻地区乃至全国土地关系的进一步认识和理解。<div><h2>\n<a name=\"3\" target=\"_blank\"> </a>作品目录<span>/徽州土地关系 </span><a href=\"http://www.baike.com/wiki/%E5%BE%BD%E5%B7%9E%E5%9C%9F%E5%9C%B0%E5%85%B3%E7%B3%BB\" target=\"_blank\">编辑</a>\n</h2>\n</div>\n<p></p><br/>总序<br/>自序<br/>第一章 政区和自然条件<br/>第一节 政区沿革<br/>第二节 生态环境<br/>第三节 人地关系<br/>第二章 土地所有制形态<br/>第一节 封建地主所有制<br/>第二节 自耕农所有制<br/>第三节 官田<br/>第四节 小买田<br/>第五节 明清徽州土地占有状况蠡测<br/>第三章 土地使用关系<br/>第一节 佃农<br/>第二节 佃仆<br/>第三节 棚户<br/>第四章 土地权属的转移<br/>第一节 土地的买卖<br/>第二节 土地的典当<br/>第五章 土地管理<br/>第一节 政府的管理<br/>第二节 乡里和公堂的管理<br/>第六章 赋役<br/>第一节 明代的田赋与粮长制度<br/>第二节 均徭和一条鞭法<br/>第三节 清代“摊丁入亩”在徽州的实施<br/>附录一 宋元土地契约<br/>附录二 明代祁门胡氏佃仆文约<br/>后记<br/>《徽州文化全书》后记<br/><br/>\n<div></div>\n</div>",
    "author": null,
    "cates": [],
    "tags": [],
    "createdAt": null,
    "updatedAt": null
    }
    ]

     */

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CateRepository cateRepository;

    @Autowired
    private LogRepository logRepository;

    /**
     * @api {get} /api/article/all 获取全部文章
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiSuccess {Object[]} articles 文章列表
     * @apiSuccess {Number}    articles.id         id
     * @apiSuccess {String}    articles.title      标题
     *
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity all() {
        List articles = articleRepository.findAll();
        List json = new ArrayList();
        for (int i = 0; i < articles.size(); i++) {
            Map obj = new HashMap();
            Article a = (Article) articles.get(i);

            Set<Cate> detailCates = a.getCates();
            Set cateIds = new HashSet();
            for (Cate cate : detailCates) {
                cateIds.add(cate.getId());
            }

            obj.put("id", a.getId());
            obj.put("cates", cateIds);
            obj.put("title", a.getTitle());
            json.add(obj);
        }
        return new ResponseEntity(json, HttpStatus.OK);
    }

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
     *
     * @apiUse multiarticle
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
     * @apiSuccessExample {json} 200
     *      {
        "id": 1,
        "title": "徽州人怎么用年俗凝聚宗族亲情？",
        "content": "<article>“宋公及楚人战于泓。宋人既成列，楚人未既济。司马曰：“彼众我寡，及其未既济也，请击之。”公曰：“不可。”既济而未成列，又以告。公曰：“未可。”既陈而后击之，宋师败绩。 公伤股，门官歼焉。”</article>",
        "author": null,
        "cates": [],
        "tags": [],
        "createdAt": null,
        "updatedAt": null
    }
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
     * @apiUse multiarticle
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "word", defaultValue = "") String word) {
        if (word == "") {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(articleRepository.findByTitleContaining(word), HttpStatus.OK);
    }

    /**
     * @api {post} /api/article/add 添加文章
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {String} title 标题
     * @apiParam {String} content 内容 (html)
     * @apiParam {String} [author] 作者
     * @apiParam {String} [cates] 分类id (多个用英文逗号分隔)
     * @apiSuccess {Number}    id         id
     * @apiSuccess {String}    title      标题
     * @apiSuccess {String}    content    内容 (html)
     * @apiSuccess {String}    author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  cates      分类
     * @apiSuccess {Object[]}  tags       标签
     * @apiSuccess {Number}    createdAt  创建时间的时间戳
     * @apiSuccess {Number}    updatedAt  修改时间的时间戳
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity add(@RequestParam(value = "title", required = true) String title,
                              @RequestParam(value = "content", required = true) String content,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "cates", required = false) String cates) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        if (author != null) article.setAuthor(author);
        if (cates != null) {
            Set toSetCates = new HashSet();
            String[] cateIds = cates.split(",");
            for (int i = 0; i < cateIds.length; i++) {
                Cate cate = cateRepository.getOneById(Integer.parseInt(cateIds[i]));
                toSetCates.add(cate);
            }
            article.setCates(toSetCates);
        }
        articleRepository.save(article);
        return new ResponseEntity(article, HttpStatus.OK);
    }

    /**
     * @api {post} /api/article/:id/edit 修改文章
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {String} [title] 标题
     * @apiParam {String} [content] 内容
     * @apiParam {String} [author] 作者
     * @apiParam {String} [cates] 分类id (多个用英文逗号分隔)
     * @apiSuccess {Number}    id         id
     * @apiSuccess {String}    title      标题
     * @apiSuccess {String}    content    内容 (html)
     * @apiSuccess {String}    author     作者 (单纯用于显示)
     * @apiSuccess {Object[]}  cates      分类
     * @apiSuccess {Object[]}  tags       标签
     * @apiSuccess {Number}    createdAt  创建时间的时间戳
     * @apiSuccess {Number}    updatedAt  修改时间的时间戳
     *
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity update(@PathVariable(value = "id") Integer id,
                                 @RequestParam(value = "title", required = true) String title,
                                 @RequestParam(value = "content", required = true) String content,
                                 @RequestParam(value = "author", required = false) String author,
                                 @RequestParam(value = "cates", required = false) String cates) {
        Article article = articleRepository.findOneById(id);
        if (article == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (title != null) article.setTitle(title);
        if (author != null) article.setAuthor(author);
        if (content != null) article.setContent(content);
        if (cates != null) {
            Set toSetCates = new HashSet();
            String[] cateIds = cates.split(",");
            for (int i = 0; i < cateIds.length; i++) {
                Cate cate = cateRepository.getOneById(Integer.parseInt(cateIds[i]));
                toSetCates.add(cate);
            }
            article.setCates(toSetCates);
        }
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
        articleRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @api {post} /api/article/:id/cate/add 给文章添加分类
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {Number} id 文章id
     * @apiParam {Number} cate_id 分类id
     *
     * @apiSuccess {String} status 状态
     * @apiSuccess {String} msg 信息
     * @apiSuccess {Number} id 文章id
     * @apiSuccess {Number} cate_id 分类id
     *
     * @apiError {String} status 状态
     * @apiError {String} error 错误种类
     * @apiError {String} msg 错误信息
     *
     */
    @RequestMapping(value = "/{id}/cate/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addCate(@PathVariable(value = "id") Integer article_id,
                                  @RequestParam(value = "cate_id", required = true) Integer cate_id) {
        Article article = articleRepository.findOneById(article_id);

        Map json = new HashMap();
        if (article == null) {
            json.put("status", "error");
            json.put("error", "ArticleError");
            json.put("msg", "article is not exist");
            return new ResponseEntity(json, HttpStatus.OK);
        }

        Cate cate = cateRepository.getOneById(cate_id);
        if (cate == null) {
            json.put("status", "error");
            json.put("error", "CateError");
            json.put("msg", "cate is not exist");
            return new ResponseEntity(json, HttpStatus.OK);
        }

        Set cates = article.getCates();
        cates.add(cate);
        article.setCates(cates);
        articleRepository.save(article);


        json.put("status", "success");
        json.put("msg", "add article to cate");
        json.put("article_id", article_id);
        json.put("cate_id", cate_id);
        return new ResponseEntity(json, HttpStatus.OK);
    }

    /**
     * @api {post} /api/article/:id/cate/remove 给文章删除分类
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup article
     * @apiParam {Number} id 文章id
     * @apiParam {Number} cate_id 分类id
     *
     * @apiSuccess {String} status 状态
     * @apiSuccess {String} msg 信息
     * @apiSuccess {Number} id 文章id
     * @apiSuccess {Number} cate_id 分类id
     *
     * @apiError {String} status 状态
     * @apiError {String} error 错误种类
     * @apiError {String} msg 错误信息
     *
     */
    @RequestMapping(value = "/{id}/cate/remove", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity removeCate(@PathVariable(value = "id") Integer article_id,
                                  @RequestParam(value = "cate_id", required = true) Integer cate_id) {
        Article article = articleRepository.findOneById(article_id);

        Map json = new HashMap();
        if (article == null) {
            json.put("status", "error");
            json.put("error", "ArticleError");
            json.put("msg", "article is not exist");
            return new ResponseEntity(json, HttpStatus.OK);
        }

        Cate cate = cateRepository.getOneById(cate_id);
        if (cate == null) {
            json.put("status", "error");
            json.put("error", "CateError");
            json.put("msg", "cate is not exist");
            return new ResponseEntity(json, HttpStatus.OK);
        }

        Set cates = article.getCates();
        cates.remove(cate);
        article.setCates(cates);
        articleRepository.save(article);


        json.put("status", "success");
        json.put("msg", "add article to cate");
        json.put("article_id", article_id);
        json.put("cate_id", cate_id);
        return new ResponseEntity(json, HttpStatus.OK);
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
     * @apiUse multiarticle
     *
     * @apiErrorExample 参数错误
     *      HTTP/1.1 400 Bad Request
     *
     */
    @RequestMapping(value = "/recently", method = RequestMethod.GET)
    public ResponseEntity recentlyReaded(HttpServletRequest req,
                                         @RequestParam(value = "ip", required = false) String ip,
                                         @RequestParam(value = "ua", required = false) String ua) {
        if (ip == null) ip = req.getRemoteHost();
        if (ua == null) ua = req.getHeader("User-Agent");

        if (ua == null) {
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
     * @apiUse multiarticle
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