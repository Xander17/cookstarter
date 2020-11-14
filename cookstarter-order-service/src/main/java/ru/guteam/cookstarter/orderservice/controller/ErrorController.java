package ru.guteam.cookstarter.orderservice.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.guteam.cookstarter.api.dto.StatusResponse;
import ru.guteam.cookstarter.orderservice.exception.AuthorizationException;
import ru.guteam.cookstarter.orderservice.exception.OrderProcessingException;
import ru.guteam.cookstarter.orderservice.exception.TokenNotValidException;

import java.net.ConnectException;

import static org.springframework.http.HttpStatus.*;
import static ru.guteam.cookstarter.api.dto.RequestMessageHeaders.JWT_TOKEN_HEADER;
import static ru.guteam.cookstarter.orderservice.controller.util.StatusResponseBuilder.error;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({OrderProcessingException.class, TokenNotValidException.class})
    public ResponseEntity<StatusResponse> badRequest(RuntimeException e) {
        log.error(String.format("Ошибка '%s'", e.getMessage()));
        return error(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({InvalidFormatException.class, MethodArgumentNotValidException.class, MismatchedInputException.class, MissingRequestHeaderException.class})
    public ResponseEntity<StatusResponse> invalidFormat(Exception e) {
        log.error("Ошибка в запросе", e);
        return error(BAD_REQUEST, "Ошибка в запросе");
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<StatusResponse> connectionLost(ConnectException e) {
        log.error("Нет ответа от сервиса", e);
        return error(INTERNAL_SERVER_ERROR, "Нет соединения");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<StatusResponse> tokenError(WebRequest request, JwtException e) {
        log.error("Ошибка при проверке токена '{}':\n{}", request.getHeader(JWT_TOKEN_HEADER), e.getMessage());
        return error(BAD_REQUEST, "Ошибка при проверке токена");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StatusResponse> authError(RuntimeException e) {
        log.error("Ошибка при aвторизации: {}", e.getMessage());
        return error(UNAUTHORIZED, e.getMessage());
    }

    // любые другие незапланированные ошибки
    @ExceptionHandler
    public ResponseEntity<StatusResponse> exception(Exception e) {
        log.error("Неизвестная ошибка", e);
        return error(INTERNAL_SERVER_ERROR);
    }
}
