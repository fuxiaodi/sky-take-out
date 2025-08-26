package com.sky.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 自定义切面，实现公共字段自动填充处理逻辑
 */

@Aspect //要让成为切面，加上这个注解
@Component //要加入spring 容器中管理
@Slf4j  //加入日志
public class AutoFillAspect {

    /**
     * 切入点，对哪些类和方向进行拦截
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}


    /**
     * 前置通知,在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()") //
    public void autoFill(JoinPoint joinPoint){ //拦截的参数值
        log.info("开始对公共字段进行填充...");

    }
}
