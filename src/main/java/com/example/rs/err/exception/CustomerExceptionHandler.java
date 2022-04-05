package com.example.rs.err.exception;
import com.example.rs.err.TooManyRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomerExceptionHandler {
    /**
     * 针对 Control 层强制性参数校验不通过时拦截并返回错误内容。
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", 1);
        body.put("msg", e.getMessage());
        return body;
    }
    @ExceptionHandler(TooManyRequest.class)
    public Map<String, Object> ConstraintViolationExceptionHandler(TooManyRequest e) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", e.getCode());
        body.put("msg", e.getMessage());
        return body;
    }


}
