package com.example.rs.filter;

import com.example.rs.control.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

@Order(0)
@WebFilter(filterName = "DefaultGobalFilter", urlPatterns = "/*")
public class DefaultGobalFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(A.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        logger.info("默认过滤器已启用.");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        logger.info("默认过滤器已销毁.");

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("拦截了一次请求");
        Map<String, String[]> map = servletRequest.getParameterMap();

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
