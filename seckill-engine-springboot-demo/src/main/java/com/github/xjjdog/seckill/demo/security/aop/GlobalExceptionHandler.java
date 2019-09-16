package com.github.xjjdog.seckill.demo.security.aop;

import com.github.xjjdog.seckill.demo.common.model.ResponseData;
import com.github.xjjdog.seckill.demo.common.util.ResponseUtil;
import com.github.xjjdog.seckill.demo.security.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {


    /**
     * 权限异常处理
     */
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseData noPermission(PermissionException e) {

        log.error("no permission error:", e);
        return ResponseUtil.error(e.getCode().toString(),e.getMessage());
    }


    /**
     * 未知运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseData notFount(RuntimeException e) {

        log.error("unknown error:", e);
        return ResponseUtil.error("500",e.getMessage());
    }
}
