package ru.guteam.customer_service.exceptions;

import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> JWTSignatureException(Exception e) {
        log.error("Подлинность токена не установлена", e);
        return new ResponseEntity<>("Подлинность токена не установлена", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> resourceNotReadableException(Exception e) {
        log.error("Ошибка в предоставленных данных. \n" + e);
        String[] exceptionCause = e.getCause().toString().split("\n");
        return new ResponseEntity<>("Ошибка в предоставленных данных. " + exceptionCause[0], HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> exception(Exception e) {
        log.error("Что-то пошло не так. Пожалуйста, обратитесь в техническую поддержку", e);
        return new ResponseEntity<>("Что-то пошло не так. Пожалуйста, обратитесь в техническую поддержку", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
