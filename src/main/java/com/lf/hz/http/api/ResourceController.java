package com.lf.hz.http.api;

import com.lf.hz.config.Config;
import com.lf.hz.model.Resource;
import com.lf.hz.repository.ResourceRepository;
import com.lf.hz.service.ResourceService;
import com.lf.hz.service.TTSService;
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
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private TTSService ttsService;

    @Autowired
    private ResourceService resourceService;

    /**
     * @api {get} /api/resource/list 获取所有静态资源
     * @apiVersion 0.0.1
     * @apiGroup resource
     * @apiParam {String} [path=/] 目录
     *
     * @apiParam {Object[]} files 该目录下的文件列表
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity resource(@RequestParam(value = "path", defaultValue = "/") String path) {
        File listPath = resourceService.getLocalFileByPath(path);
        if (!listPath.isDirectory()) {
            HashMap json = new HashMap();
            json.put("status", "error");
            json.put("msg", "path not a directory or path not exists");
            return new ResponseEntity(json, HttpStatus.FORBIDDEN);
        }

        ArrayList files = new ArrayList();
        for (File f : listPath.listFiles()) {
            HashMap file = new HashMap();
            String relativePath = f.getAbsolutePath().substring(config.getResoucesPath().length());
            file.put("name", f.getName());
            file.put("size", f.length());
            file.put("modified", f.lastModified());
            file.put("path", relativePath);
            Resource resource = resourceRepository.getOneByPath(relativePath);
            if (resource != null) {
                file.put("link", resource.getLink());
                file.put("description", resource.getDescription());
            }
            file.put("isdir", f.isDirectory());
            if (!f.isDirectory()) {
                file.put("url", resourceService.getURLByRelativePath(relativePath));
            }
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

        File listPath = resourceService.getLocalFileByPath(path);
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

        String relativeFilePath = Paths.get(path, file.getOriginalFilename()).toString();

        if (relativeFilePath.charAt(0) == '/') {
            relativeFilePath = relativeFilePath.substring(1);
        }

        Resource resource = resourceRepository.getOneByPath(relativeFilePath);
        if (resource == null) {
            resource = new Resource();
        }

        Path toSaveFilePath = Paths.get(listPath.getAbsolutePath().toString(), file.getOriginalFilename());
        Files.write(toSaveFilePath, file.getBytes());

        resource.setName(toSaveFilePath.getFileName().toString());
        resource.setPath(relativeFilePath);
        resourceRepository.save(resource);

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

        File listPath = resourceService.getLocalFileByPath(path);
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

    /**
     * @api {get} /api/resource/get 获取一个资源的属性
     * @apiVersion 0.0.1
     * @apiGroup resource
     * @apiParam {String} path 相对路径
     *
     * @apiSuccess {Number} id 资源id
     * @apiSuccess {String} name 文件名
     * @apiSuccess {String} path 相对路径
     * @apiSuccess {String} link 资源跳转到的链接
     * @apiSuccess {String} description 资源描述
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(value = "path", required = true) String path) {
        Resource resource = resourceRepository.getOneByPath(path);
        if (resource == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(resource, HttpStatus.OK);
    }

    /**
     * @api {get} /api/resource/edit-info 设置一个资源的属性
     * @apiVersion 0.0.1
     * @apiGroup resource
     * @apiParam {String} path 相对路径
     * @apiParam {String} [description] 资源描述
     * @apiParam {String} [link] 资源跳转到的链接
     *
     * @apiSuccess {Number} id 资源id
     * @apiSuccess {String} name 文件名
     * @apiSuccess {String} path 相对路径
     * @apiSuccess {String} link 资源跳转到的链接
     * @apiSuccess {String} description 资源描述
     */
    @RequestMapping(value = "/edit-info", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity editInfo(@RequestParam(value = "path", required = true) String path,
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "link", required = false) String link) {
        Resource resource = resourceRepository.getOneByPath(path);
        if (resource == null) {
            resource = new Resource();
            resource.setPath(path);
            resource.setName(Paths.get(path).getFileName().toString());
        }

        if (description != null) resource.setDescription(description);
        if (link != null) resource.setLink(link);
        resourceRepository.save(resource);
        return new ResponseEntity(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/tts", method = RequestMethod.POST)
    public ResponseEntity tts(@RequestParam(value = "articleid", required = false) Integer id,
                              @RequestParam(value = "text", required = false) String text) throws IOException, NoSuchAlgorithmException {
        HashMap json = new HashMap();
        if (text != null) {
            json.put("status", "success");
            json.put("soundpath", ttsService.getTextSound(text));
            return new ResponseEntity(json, HttpStatus.OK);
        } else if (id != null) {
            json.put("status", "success");
            json.put("soundpath", ttsService.getArticleSound(id));
            return new ResponseEntity(json, HttpStatus.OK);
        } else {
            json.put("status", "error");
            json.put("msg", "BAD REQUEST");
            return new ResponseEntity(json, HttpStatus.BAD_REQUEST);
        }
    }
}
