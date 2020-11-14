package ru.guteam.restaurantservice.exception;


public class DishNotFountException extends NotFountException {
    public DishNotFountException(Long id) {
        super("Dish with id: '" + id + "' not found");
    }
}
