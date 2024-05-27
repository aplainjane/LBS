package com.team.demo.generator.controller.jwt;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * JWT过滤器，拦截 /secure的请求
 */
@Slf4j
@WebFilter(urlPatterns = "/secure/*")
public class JwtFilter implements Filter {

    private static final long MAX_REQUESTS_PER_MINUTE = 60; // 每分钟最大请求数
    private static final long TIME_WINDOW_MILLIS = TimeUnit.MINUTES.toMillis(1); // 时间窗口大小（毫秒）

    private final ConcurrentHashMap<String, UserRequestInfo> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    private static final int TOO_MANY_REQUESTS = 429;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;




        response.setCharacterEncoding("UTF-8");
        final String token = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "没有token！");
            return;
        }

        Map<String, Claim> userData = JwtUtil.verifyToken(token);
        if (userData == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token不合法！");
            return;
        }

        String clientIp = request.getRemoteAddr();
        long currentTimeMillis = System.currentTimeMillis();

        // 限速检查
        UserRequestInfo userRequestInfo = requestCounts.computeIfAbsent(clientIp, k -> new UserRequestInfo());
        synchronized (userRequestInfo) {
            if (currentTimeMillis - userRequestInfo.timestamp > TIME_WINDOW_MILLIS) {
                userRequestInfo.timestamp = currentTimeMillis;
                userRequestInfo.requestCount = 0;
            }
            userRequestInfo.requestCount++;
            if (userRequestInfo.requestCount > MAX_REQUESTS_PER_MINUTE) {
                response.sendError( TOO_MANY_REQUESTS, "请求过于频繁，请稍后再试！");
                return;
            }
        }

        Integer id = userData.get("id").asInt();
        String userName = userData.get("userName").asString();
        request.setAttribute("id", id);
        request.setAttribute("userName", userName);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }

    private static class UserRequestInfo {
        long timestamp;
        int requestCount;
    }
}
