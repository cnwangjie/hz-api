package com.lf.hz.http.api;

import com.lf.hz.repository.NavRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @apiDefine nav 导航栏
 */

@RestController
@RequestMapping("/api/nav")
public class NavController {

    @Autowired
    private NavRepository navRepository;

    /**
     * @api {get} /api/nav 获得导航栏条目
     * @apiVersion 0.0.1
     * @apiGroup nav
     *
     * @apiSuccess {Object[]}  navs 导航链接列表
     * @apiSuccess {Number}    navs.id         id
     * @apiSuccess {String}    navs.title      显示的内容
     * @apiSuccess {String}    navs.link        跳转至的链接
     * @apiSuccess {String}    navs.name       路由名称 (nullable)
     * @apiSuccess {Number}    navs.createdAt  创建时间的时间戳
     * @apiSuccess {Number}    navs.updatedAt  修改时间的时间戳
     *
     * @apiSuccessExample {json} 200
     * [
    {
    "id": 1,
    "title": "首页",
    "link": "/",
    "name": "home",
    "createdAt": 1499416490000,
    "updatedAt": 1499416490000
    },
    {
    "id": 2,
    "title": "归档",
    "link": "",
    "name": null,
    "createdAt": 1499416490000,
    "updatedAt": 1499416490000
    },
    {
    "id": 3,
    "title": "新闻",
    "link": "",
    "name": null,
    "createdAt": 1499416490000,
    "updatedAt": 1499416490000
    },
    {
    "id": 4,
    "title": "服务",
    "link": "",
    "name": null,
    "createdAt": 1499416490000,
    "updatedAt": 1499416490000
    },
    {
    "id": 5,
    "title": "介绍",
    "link": "",
    "name": null,
    "createdAt": 1499416490000,
    "updatedAt": 1499416490000
    },
    {
    "id": 6,
    "title": "联系",
    "link": "",
    "name": null,
    "createdAt": 1499416490000,
    "updatedAt": 1499416490000
    }
    ]
     */
    @RequestMapping("")
    public ResponseEntity index() {
        return new ResponseEntity(navRepository.findAll(), HttpStatus.OK);
    }
}
