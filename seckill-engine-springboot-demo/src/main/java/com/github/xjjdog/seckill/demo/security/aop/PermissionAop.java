package com.github.xjjdog.seckill.demo.security.aop;

import com.github.xjjdog.seckill.demo.security.annotation.Permission;
import com.github.xjjdog.seckill.demo.security.resolver.AnnotationResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class PermissionAop {



    @Pointcut(value = "@annotation(com.github.xjjdog.seckill.demo.security.annotation.Permission)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {

        handle(point);
        return point.proceed();
    }

    private void handle(ProceedingJoinPoint point) throws Exception {
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("this annotation can only be used for method");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        Permission annotation = currentMethod.getAnnotation(Permission.class);
        Object taskId = AnnotationResolver.newInstance().resolver(point, annotation.taskId());
        Object userId = AnnotationResolver.newInstance().resolver(point, annotation.userId());
        //todo 秒杀任务 userid验证逻辑  PermissionException();

    }
}