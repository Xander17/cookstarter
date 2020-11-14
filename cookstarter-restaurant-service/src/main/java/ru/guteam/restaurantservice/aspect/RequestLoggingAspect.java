package ru.guteam.restaurantservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class RequestLoggingAspect {

    @Before("execution(* ru.guteam.restaurantservice.controller.*.* (..))")
    public void setLog(JoinPoint joinPoint) {
        log.info("CONTROLLER method invoking - " + joinPoint.getSignature().getName());
    }
}
