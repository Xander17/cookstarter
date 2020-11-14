package ru.guteam.picture_service.controller;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.guteam.cookstarter.api.dto.StatusResponse;
import ru.guteam.picture_service.exception.AuthorizationException;
import ru.guteam.picture_service.exception.NotPictureException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static ru.guteam.cookstarter.api.dto.RequestMessageHeaders.JWT_TOKEN_HEADER;
import static ru.guteam.picture_service.controller.util.StatusResponseBuilder.error;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<StatusResponse> tokenError(WebRequest request, JwtException e) {
        log.error("Ошибка при проверке токена '{}':\n{}", request.getHeader(JWT_TOKEN_HEADER), e.getMessage());
        return error(UNAUTHORIZED, "Ошибка при проверке токена");
    }

    @ExceptionHandler(NotPictureException.class)
    public ResponseEntity<StatusResponse> pictureError(NotPictureException e) {
        log.error("Ошибка при получении картинки '{}'", e.getMessage());
        return error(UNAUTHORIZED, "Ошибка при получении картинки");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StatusResponse> authError(RuntimeException e) {
        log.error("Ошибка авторизации: {}", e.getMessage());
        return error(UNAUTHORIZED, e.getMessage());
    }
}
