package com.lf.hz.http.filter;

import com.lf.hz.model.Log;
import com.lf.hz.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogFilter implements Filter {

    @Autowired
    private LogRepository logRepository;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        if (!request.getMethod().equalsIgnoreCase("GET")) {
            chain.doFilter(req, res);
            return;
        }
        Log log = new Log();
        log.setUa(request.getHeader("User-Agent"));
        log.setIp(request.getRemoteHost());
        log.setPage(request.getRequestURI());
        logRepository.save(log);
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {

    }
}
