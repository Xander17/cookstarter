package ru.guteam.customer_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.guteam.customer_service.controllers.utils.TokenResponse;
import ru.guteam.customer_service.controllers.utils.TokenRequest;
import ru.guteam.customer_service.entities.utils.SystemCustomer;
import ru.guteam.customer_service.entities.utils.SystemRestaurant;

import java.util.Objects;

@Slf4j
@Aspect
@Configuration
public class LoggingRequestAspect {

    // логирование попытки аутентификации пользователей
    @Before("execution(* ru.guteam.customer_service.controllers.AuthController.* (..))")
    public void logAuthRequest(JoinPoint joinPoint) {
        TokenRequest request = (TokenRequest) joinPoint.getArgs()[0];
        log.info("Запрос на аутентификацию пользователя с логином: " + request.getUsername() +
                " и паролем: " + request.getPassword());
    }

    // логирование результатов аутентификации пользователей
    @AfterReturning(pointcut = "execution(* ru.guteam.customer_service.controllers.AuthController.* (..))", returning = "result")
    public void logAuthResponse(JoinPoint joinPoint, Object result) {
        ResponseEntity response = (ResponseEntity) result;
        TokenRequest authRequest = (TokenRequest) joinPoint.getArgs()[0];
        if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            log.info("Пользователь с логином: " + authRequest.getUsername() +
                    " и паролем: " + authRequest.getPassword() + " не обнаружен");
            return;
        }
        if (response.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(response.getBody()).getClass() != null) {
                TokenResponse customerResponse = (TokenResponse) response.getBody();
                log.info("Для клиента с логином: " + authRequest.getUsername() +
                        " и паролем: " + authRequest.getPassword() + " сгенерирован токен: " + customerResponse.getToken());
        }
    }

    // логирование попытки регистрации клиентов
    @Before("execution(* ru.guteam.customer_service.controllers.RegistrationController.customerRegistration(..))")
    public void logCustomerRegRequest(JoinPoint joinPoint) {
        SystemCustomer customer = (SystemCustomer) joinPoint.getArgs()[0];
        log.info("Запрос на регистрацию клиента с логином: " + customer.getUsername());
    }

    // логирование попытки регистрации ресторанов
    @Before("execution(* ru.guteam.customer_service.controllers.RegistrationController.restaurantRegistration(..))")
    public void logRestaurantRegRequest(JoinPoint joinPoint) {
        SystemRestaurant restaurant = (SystemRestaurant) joinPoint.getArgs()[0];
        log.info("Запрос на регистрацию ресторана с логином: " + restaurant.getUsername());
    }

    // логирование результатов регистрации ресторанов
    @AfterReturning(pointcut = "execution(* ru.guteam.customer_service.controllers.RegistrationController.customerRegistration (..))", returning = "result")
    public void logCustomerRegResponse(JoinPoint joinPoint, Object result) {
        ResponseEntity response = (ResponseEntity) result;
        SystemCustomer customer = (SystemCustomer) joinPoint.getArgs()[0];
        if (response.getStatusCode().equals(HttpStatus.CONFLICT)) {
            log.info("Невозможно зарегистрировать. Логин: " + customer.getUsername() + " и/или адрес электронной почты: " +
                    customer.getEmail() + " уже существует.");
        }
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("Клиент с логином: " + customer.getUsername() +
                    " и паролем: " + customer.getPassword() + " зарегистрирован");
        }
    }

    // логирование результатов регистрации клиентов
    @AfterReturning(pointcut = "execution(* ru.guteam.customer_service.controllers.RegistrationController.restaurantRegistration (..))", returning = "result")
    public void logRestaurantRegResponse(JoinPoint joinPoint, Object result) {
        ResponseEntity response = (ResponseEntity) result;
        SystemRestaurant restaurant = (SystemRestaurant) joinPoint.getArgs()[0];
        if (response.getStatusCode().equals(HttpStatus.CONFLICT)) {
            log.info("Невозможно зарегистрировать. Ресторан с логином: " + restaurant.getUsername() + " уже существует");
        }
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("Ресторан с логином: " + restaurant.getUsername() +
                    " и паролем: " + restaurant.getPassword() + " зарегистрирован");
        }
    }
}
