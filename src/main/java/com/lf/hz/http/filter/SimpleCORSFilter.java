package com.lf.hz.http.filter;

import com.lf.hz.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {

    @Autowired
    private Config config;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", config.getJwtHeader());
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {}

    public void init(FilterConfig filterConfig) {}
}
