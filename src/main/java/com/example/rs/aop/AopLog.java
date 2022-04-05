package com.example.rs.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//定义为一个切面组件
@Aspect
//Spring boot组件
@Component
@Order(1)
public class AopLog {
    //日志对象
    Logger logger = LoggerFactory.getLogger(AopLog.class);

    //定义一个切点
    //匹配所有公开方法 不限返回类型 指定包下 所有方法 不限返回值
    @Pointcut("execution(public * com.example.rs.control..*.*(..))")
    public void aopWebLog() {

    }
    //之前
    @Before("aopWebLog()")
    public void requestJoinLogInfo(JoinPoint joinPoint) throws Throwable {
        joinPoint.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpRequest = attributes.getRequest();
        logger.info("一个HTTP请求被成功接收(" + httpRequest.getRequestURL() + ")");
        logger.info("查询参数: " + httpRequest.getQueryString());
        logger.info("IP地址: " + httpRequest.getRemoteAddr());
        logger.info("Cookies: " + httpRequest.getCookies());
        logger.info("类所属方法: " + joinPoint.getSignature().getName());
    }
    @AfterReturning(pointcut = "aopWebLog()", returning = "retObject")
    public void requestLeftLogInfo(Object retObject) throws Throwable {
        logger.info("应答值: " + retObject);
    }

    @AfterThrowing(pointcut = "aopWebLog()", throwing = "ex")
    public void requestThrowingInfo(JoinPoint joinPoint, Exception ex) throws Throwable {
        logger.error("执行异常: " + ex.getMessage());
    }
}