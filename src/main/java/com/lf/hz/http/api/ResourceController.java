package com.lf.hz.http.api;

import com.lf.hz.config.Config;
import com.lf.hz.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @apiDefine resource 资源
 */

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private Config config;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * @api {get} /api/resource/list 获取所有静态资源
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup resource
     * @apiParam {String} [path=/] 目录
     *
     * @apiParam {Object[]} files 该目录下的文件列表
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity resource(@RequestParam(value = "path", defaultValue = "/") String path) {
        Path resourcePath = Paths.get(config.getResoucesPath());
        File listPath = new File(Paths.get(resourcePath.toString(), path).toString());
        if (!listPath.isDirectory()) {
            HashMap json = new HashMap();
            json.put("status", "error");
            json.put("msg", "path not a directory or path not exists");
            return new ResponseEntity(json, HttpStatus.FORBIDDEN);
        }
        ArrayList files = new ArrayList();
        for (File f : listPath.listFiles()) {
            HashMap file = new HashMap();
            file.put("name", f.getName());
            file.put("size", f.length());
            file.put("modified", f.lastModified());
            file.put("path", f.getAbsolutePath().substring(config.getResoucesPath().length()));
            file.put("isdir", f.isDirectory());
            files.add(file);
        }
        return new ResponseEntity(files, HttpStatus.OK);
    }

    /**
     * @api {post} /api/resource/upload 上传静态资源
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup resource
     * @apiParam {File} file 要上传的文件
     * @apiParam {String} [path=/] 要上传到的目录
     *
     * @apiSuccess {String} status 状态
     * @apiSuccess {String} path 上传到的路径
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity upload(@RequestParam(value = "file", required = true) MultipartFile file,
                                 @RequestParam(value = "path", defaultValue = "/", required = false) String path) throws IOException {
        HashMap json = new HashMap();

        Path resourcePath = Paths.get(config.getResoucesPath());
        File listPath = new File(Paths.get(resourcePath.toString(), path).toString());
        if (!listPath.isDirectory()) {
            json.put("status", "error");
            json.put("msg", "path not a directory or path not exists");
            return new ResponseEntity(json, HttpStatus.FORBIDDEN);
        }

        if (file.isEmpty()) {
            json.put("status", "error");
            json.put("msg", "file is empty");
            return new ResponseEntity(json, HttpStatus.BAD_REQUEST);
        }
        Path toSaveFilePath = Paths.get(listPath.getAbsolutePath().toString(), file.getOriginalFilename());
        Files.write(toSaveFilePath, file.getBytes());

        json.put("status", "success");
        json.put("msg", "upload successfully");
        json.put("file", toSaveFilePath.toString().substring(config.getResoucesPath().length()));
        return new ResponseEntity(json, HttpStatus.OK);
    }

    /**
     * @api {post} /api/resource/mkdir 创建目录
     * @apiPermission ADMIN
     * @apiHeader Authorization JWT token
     * @apiVersion 0.0.1
     * @apiGroup resource
     * @apiParam {String} [path=/] 父目录
     * @apiParam {String} name 目录名
     *
     * @apiSuccess {String} status 状态
     * @apiSuccess {String} msg 信息
     * @apiSuccess {String} path 创建的路径
     */
    @RequestMapping(value = "/mkdir", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity mkdir(@RequestParam(value = "path", defaultValue = "/", required = false) String path,
                                @RequestParam(value = "name", required = true) String name) {

        HashMap json = new HashMap();

        Path resourcePath = Paths.get(config.getResoucesPath());
        File listPath = new File(Paths.get(resourcePath.toString(), path).toString());
        if (!listPath.isDirectory()) {
            json.put("status", "error");
            json.put("msg", "path not a directory or path not exists");
            return new ResponseEntity(json, HttpStatus.FORBIDDEN);
        }

        File dir = new File(Paths.get(listPath.getAbsolutePath().toString(), name).toString());
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info(dir.getAbsolutePath());
        if (dir.exists()) {
            json.put("status", "error");
            json.put("msg", "directory exists");
            return new ResponseEntity(json, HttpStatus.FORBIDDEN);
        }

        dir.mkdir();

        json.put("status", "success");
        json.put("msg", "make directory successfully");
        json.put("path", dir.getAbsolutePath().substring(config.getResoucesPath().length()));
        return new ResponseEntity(json, HttpStatus.OK);
    }
}
