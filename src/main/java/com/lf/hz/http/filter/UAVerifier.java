package com.lf.hz.http.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UAVerifier implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
        String ua = req.getHeader("User-Agent");
        if (ua != null) return true;

        res.setStatus(400);
        res.setHeader("Content-Type", "application/json");
        res.getWriter().print("{\"status\": \"error\", \"msg\": \"need http request header: User-Agent\"}");
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}
}
