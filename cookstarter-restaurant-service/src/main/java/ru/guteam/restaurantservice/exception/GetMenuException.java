package ru.guteam.restaurantservice.exception;


public class GetMenuException extends NotFountException {
    public GetMenuException(Long restaurantId) {
        super("Menu with restaurant id: '" + restaurantId + "' not found.");
    }
}
