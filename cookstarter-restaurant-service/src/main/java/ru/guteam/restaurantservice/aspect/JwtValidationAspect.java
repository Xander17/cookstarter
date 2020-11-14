package ru.guteam.restaurantservice.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import ru.guteam.restaurantservice.util.JwtValidator;

@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class JwtValidationAspect {
    private final JwtValidator jwtValidator;


    @Before("execution(* ru.guteam.restaurantservice.controller.*.* (java.lang.String, ..))")
    public void checkToken(JoinPoint joinPoint) {

        String token = (String) joinPoint.getArgs()[0];
        log.info("Checking token.");
        jwtValidator.checkJwt(token);
    }
}
