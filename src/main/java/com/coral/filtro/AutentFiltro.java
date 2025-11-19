package com.coral.filtro;

import java.io.IOException;

import com.coral.util.TokenUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AutentFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String path = req.getRequestURI();
        String contextPath = req.getContextPath();
        
        if (path.endsWith("login") || path.endsWith(".jsp") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith("/")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken != null && TokenUtil.validateToken(jwtToken)) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contextPath + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}