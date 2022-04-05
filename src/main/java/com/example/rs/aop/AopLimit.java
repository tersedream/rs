package com.example.rs.aop;

import com.example.rs.annotation.Limit;
import com.example.rs.err.TooManyRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
@Order(2)
public class AopLimit {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Pointcut("execution(public * com.example.rs.control..*.*(..)) && @annotation(com.example.rs.annotation.Limit)")
    public void limit() {

    }

    @Before("limit()")
    public void requestJoinLogInfo(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpRequest = attributes.getRequest();
        String methodName = joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName();
        String key = httpRequest.getRemoteAddr() + methodName;
        Limit limit = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(Limit.class);
        redisTemplate.opsForValue().setIfAbsent(key, 0);
        Long total = redisTemplate.opsForValue().increment(key);
        if (total > limit.total()) {
            throw new TooManyRequest("访问过于频繁!", 1000);
        }
    }
}
