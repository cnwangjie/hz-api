package com.lf.hz.http.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IpVerifier implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
        String ip = req.getRemoteHost();
        if (isIntranetIp(ip)) return true;

        res.setStatus(200);
        res.setHeader("Content-Type", "application/json");
        res.getWriter().print("{\"status\": \"error\", \"msg\": \"restrict intranet visit\"}");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean isIntranetIp(String ip) {
        String[] ipPartStr = ip.split("\\.");
        if (ipPartStr.length < 4) return true;

        int[] ipPart = new int[4];
        for (int i = 0; i < 4; i += 1) {
            ipPart[i] = Integer.parseInt(ipPartStr[i]);
        }
        boolean result = ipPart[0] == 127 || ipPart[0] == 172;
        return result;
    }
}
