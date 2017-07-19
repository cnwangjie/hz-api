package com.lf.hz.http.api;

import com.lf.hz.model.Tag;
import com.lf.hz.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @apiDefine tag 标签
 */

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    /**
     * @api {get} /api/tag/all 获取全部标签
     * @apiVersion 0.0.1
     * @apiGroup tag
     *
     * @apiSuccessExample {json} 成功
     * [
    {
    "id": 1,
    "name": "tag 0",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 2,
    "name": "tag 1",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 3,
    "name": "tag 2",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 4,
    "name": "tag 3",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    },
    {
    "id": 5,
    "name": "tag 4",
    "createdAt": 1498741479000,
    "updatedAt": 1498741479000
    }
    ]
     *
     * @apiErrorExample {json} 404
     *      HTTP/1.1 404 Not Found
     *      {
     *          "status": "error",
     *          "error": "TagError",
     *          "msg": "this tag is not exist"
     *      }
     */
    @RequestMapping("/all")
    public ResponseEntity all() {
        return new ResponseEntity(tagRepository.findAll(), HttpStatus.OK);
    }

    /**
     * @api {get} /api/tag/:id 获取一个具有一个标签的所有文章
     * @apiVersion 0.0.1
     * @apiGroup tag
     * @apiParam {Number} id 标签id
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
        Tag tag = tagRepository.getOneById(id);
        if (tag == null) {
            Map json = new HashMap();
            json.put("status", "error");
            json.put("error", "TagError");
            json.put("msg", "this tag is not exist");
            return new ResponseEntity(json, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(tag.getArticles(), HttpStatus.OK);
        }
    }
}
