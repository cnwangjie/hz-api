package com.lf.hz.http.api;

import com.lf.hz.config.Config;
import com.lf.hz.model.*;
import com.lf.hz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private Config config;

    private final ArticleRepository articleRepository;
    private final CateRepository cateRepository;
    private final PageRepository pageRepository;
    private final ResourceRepository resourceRepository;
    private final TagRepository tagRepository;
    private final NavRepository navRepository;

    @Inject
    public TestController(ArticleRepository articleRepository,
                          CateRepository cateRepository,
                          PageRepository pageRepository,
                          ResourceRepository resourceRepository,
                          TagRepository tagRepository,
                          NavRepository navRepository) {
        this.articleRepository = articleRepository;
        this.cateRepository = cateRepository;
        this.pageRepository = pageRepository;
        this.resourceRepository = resourceRepository;
        this.tagRepository = tagRepository;
        this.navRepository = navRepository;
    }


    @RequestMapping(value = "")
    public ResponseEntity index() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json"));
        return new ResponseEntity("{\"status\": \"Success!\"}", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/seed")
    public ResponseEntity seed() throws IOException {
        if (!config.getDebug()) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        for (int i = (int) cateRepository.count(); i < 5; i += 1) {
            Cate a = new Cate();
            a.setName("cate " + i);
            cateRepository.save(a);
        }

        for (int i = (int) tagRepository.count(); i < 5; i += 1) {
            Tag a = new Tag();
            a.setName("tag " + i);
            tagRepository.save(a);
        }

        for (int i = (int) articleRepository.count(); i < 20; i += 1) {
            Article a = new Article();
            a.setContent("tttttt");
            a.setAuthor("author");
            a.setTitle("title t");
            a.setCreatedAt(new Date());
            a.setUpdatedAt(new Date());
            int caten = (int) (Math.random() * 5);
            HashSet cates = new HashSet();
            for (int j = 0; j < caten; j += 1) {
                cates.add(cateRepository.getOneById((int) (Math.random() * 5) + 1));
            }
            a.setCates(cates);

            int tagn = (int) (Math.random() * 5);
            HashSet tags = new HashSet();
            for (int j = 0; j < tagn; j += 1) {
                tags.add(tagRepository.getOneById((int) (Math.random() * 5) + 1));
            }
            a.setTags(tags);
            articleRepository.save(a);
        }

        File testStaticPic = new File(config.getResoucesPath() + "pic.jpg");
        if (testStaticPic.exists() && resourceRepository.count() == 0) {
            Resource pic = new Resource();
            pic.setName(testStaticPic.getName());
            String uuid = UUID.randomUUID().toString();
            pic.setUuid(uuid);
            pic.setType("image/jpg");
            File newFile = new File(config.getResoucesPath() + uuid);
            FileChannel in = null;
            FileChannel out = null;
            FileInputStream inStream = null;
            FileOutputStream outStream = null;
            try {
                inStream = new FileInputStream(testStaticPic);
                outStream = new FileOutputStream(newFile);
                in = inStream.getChannel();
                out = outStream.getChannel();
                in.transferTo(0, in.size(), out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                inStream.close();
                outStream.close();
                in.close();
                out.close();
            }
            resourceRepository.save(pic);
        }

        if (navRepository.count() == 0) {
            Nav nav0 = new Nav();
            nav0.setLink("/");
            nav0.setName("home");
            nav0.setTitle("首页");
            navRepository.save(nav0);

            Nav nav1 = new Nav();
            nav1.setLink("#");
            nav1.setTitle("not active");
            navRepository.save(nav1);
        }

        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
