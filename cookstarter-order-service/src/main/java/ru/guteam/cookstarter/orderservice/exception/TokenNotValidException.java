package ru.guteam.cookstarter.orderservice.exception;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException(String message) {
        super(message);
    }
}
