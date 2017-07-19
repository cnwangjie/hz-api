package com.lf.hz.http;

import com.lf.hz.config.Config;
import com.lf.hz.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @apiDefine auth 认证
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private org.apache.commons.logging.Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private Config config;

    /**
     * @api {post} /auth/login 登陆
     * @apiVersion 0.0.1
     * @apiGroup auth
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     *
     * @apiSuccess {String} status 状态
     * @apiSuccess {String} msg    成功信息
     * @apiSuccess {String} token  JWT token
     *
     * @apiSuccessExample {json} 200
     * {
     *     "status": "success",
     *     "msg": "login success",
     *     "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUwMDc0Mzc2N30.glHDV2wRpyd6fQxr_BytWej1pMa7JPKwUSkf9Wmz-eXSeg4QugyHB5MxIEkKSmkc0-70QBWu-kW5bkTJKCybLg"
     * }
     *
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity getToken(@RequestParam(value = "username", required = true) String username,
                                   @RequestParam(value = "password", required = true) String password) {

        HashMap json = new HashMap();


        if (!BCrypt.checkpw(password, userRepository.getOneByUsername(username).getPassword())) {
            json.put("status", "error");
            json.put("msg", "user is not exists or password wrong");
        } else {
            Map claims = new HashMap();
            claims.put("sub", username);

            Date expirationDate = new Date((new Date()).getTime() + 360000000);

            String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, config.getJwtSecret())
                    .compact();
            json.put("status", "success");
            json.put("msg", "login success");
            json.put("token", token);
        }
        return new ResponseEntity(json, HttpStatus.OK);
    }

    /**
     * @api {post} /auth/test 身份测试
     * @apiVersion 0.0.1
     * @apiGroup auth
     * @apiHeader Authorization JWT token
     *
     * @apiSuccess {String} status 状态
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity test() {
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
