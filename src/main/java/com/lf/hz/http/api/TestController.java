package com.lf.hz.http.api;

import com.lf.hz.config.Config;
import com.lf.hz.model.*;
import com.lf.hz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private Config config;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CateRepository cateRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NavRepository navRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

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
            nav1.setLink("");
            nav1.setTitle("归档");
            navRepository.save(nav1);

            Nav nav2 = new Nav();
            nav2.setLink("");
            nav2.setTitle("新闻");
            navRepository.save(nav2);

            Nav nav3 = new Nav();
            nav3.setLink("");
            nav3.setTitle("服务");
            navRepository.save(nav3);

            Nav nav4 = new Nav();
            nav4.setLink("");
            nav4.setTitle("介绍");
            navRepository.save(nav4);

            Nav nav5 = new Nav();
            nav5.setLink("");
            nav5.setTitle("联系");
            navRepository.save(nav5);
        }

        if (authorityRepository.count() == 0) {
            Authority admin = new Authority();
            admin.setName(AuthorityName.ROLE_ADMIN);
            authorityRepository.save(admin);
        }

        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));

            List authorities = new ArrayList();
            authorities.add(authorityRepository.getOneByName(AuthorityName.ROLE_ADMIN));

            user.setAuthorities(authorities);
            userRepository.save(user);
        }

        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
