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

/**
 * @apiDefine cate 分类
 */

@RestController
@RequestMapping("/api/cate")
public class CateController {

    @Autowired
    private CateRepository cateRepository;

    /**
     * @api {get} /api/cate/all 获取全部分类
     * @apiVersion 0.0.1
     * @apiGroup cate
     *
     * @apiSuccessExample {json} 成功
     *      HTTP/1.1 200
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
}
