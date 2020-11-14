package ru.guteam.restaurantservice.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class ServiceLogginingAspect {

    @Before("execution(* ru.guteam.restaurantservice.service.impl.*.* (..))")
    public void setLog(JoinPoint joinPoint) {
        log.info("SERVICE method invoking - " + joinPoint.getSignature().getName());
    }
}
