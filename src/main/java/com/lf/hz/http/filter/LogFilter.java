package com.lf.hz.http.filter;

import com.lf.hz.model.Log;
import com.lf.hz.repository.LogRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogFilter implements Filter {

    private final LogRepository logRepository;

    @Inject
    public LogFilter(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
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
