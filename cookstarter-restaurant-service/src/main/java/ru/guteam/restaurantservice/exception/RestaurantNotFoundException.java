package ru.guteam.restaurantservice.exception;



public class RestaurantNotFoundException extends NotFountException {
    public RestaurantNotFoundException(String message) {
        super("Restaurant with: '" + message + "' not found.");
    }

    public RestaurantNotFoundException(Long id) {
        super("Restaurant with id: '" + id + "' not found.");
    }
}
