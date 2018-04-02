package com.lf.hz.http.api;

import com.lf.hz.model.Cate;
import com.lf.hz.repository.CateRepository;
import com.lf.hz.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @apiDefine cate 分类
 */

@RestController
@RequestMapping("/api/cate")
public class CateController {

    @Autowired
    private CateRepository cateRepository;

    @Autowired
    private ResourceService resourceService;

    /**
     * @api {get} /api/cate/all 获取全部分类
     * @apiVersion 0.0.1
     * @apiGroup cate
     *
     * @apiSuccessExample {json} 成功
     * [
    {
    "id": 1,
    "name": "cate 0",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 2,
    "name": "cate 1",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 3,
    "name": "cate 2",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 4,
    "name": "cate 3",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 5,
    "name": "cate 4",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    }
    ]
     *
     * @apiErrorExample {json} 404
     *      HTTP/1.1 404 Not Found
     *      {
     *          "status": "error",
     *          "error": "CateError",
     *          "msg": "this cate is not exist"
     *      }
     */
    @RequestMapping("/all")
    public ResponseEntity all() {
        return new ResponseEntity(cateRepository.findAll(), HttpStatus.OK);
    }

    /**
     * @api {get} /api/cate/:id 获取一个分类下的所有文章
     * @apiVersion 0.0.1
     * @apiGroup cate
     * @apiParam {Number} id 分类id
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
     *
     * @apiErrorExample 404
     *      HTTP/1.1 404 Not Found
     *
     */
    @RequestMapping("/{id}")
    public ResponseEntity get(@PathVariable Integer id) {
        Cate cate = cateRepository.getOneById(id);
        if (cate == null) {
            Map json = new HashMap();
            json.put("status", "error");
            json.put("error", "CateError");
            json.put("msg", "this cate is not exist");
            return new ResponseEntity(json, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(cate.getArticles(), HttpStatus.OK);
        }
    }

    /**
     * @api {get} /api/cate/add 增加新的分类
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup cate
     * @apiParam {String} name 分类名
     *
     * @apiSuccess {Number}  id 分类id
     * @apiSuccess {String} name 分类名
     *
     * @apiError {String} status 状态
     * @apiError {String} msg 错误信息
     *
     */
    @RequestMapping("/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity add(@RequestParam(value = "name", required = true) String name) {
        Map json = new HashMap();
        if (name == null) {
            json.put("status", "error");
            json.put("msg", "need name param");
            return new ResponseEntity(json, HttpStatus.BAD_REQUEST);
        }
        if (cateRepository.getOneByName(name) != null) {
            json.put("status", "error");
            json.put("msg", "cate has existed");
            return new ResponseEntity(json, HttpStatus.OK);
        }

        Cate cate = new Cate();
        cate.setName(name);

        cateRepository.save(cate);
        ArrayList<String> type = new ArrayList();
        type.add("photo");
        type.add("video");
        type.add("anime");
        for (String t : type) {
            File dir = resourceService.getLocalFileByPath(t + "/" + name);
            dir.mkdir();
        }
        return new ResponseEntity(cate, HttpStatus.OK);
    }
}
