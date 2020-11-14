package ru.guteam.restaurantservice.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super("Duplicate detected: " + message);
    }
}
