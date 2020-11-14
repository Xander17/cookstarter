package ru.guteam.restaurantservice.exception;


public class ContactNotFoundException extends NotFountException {
    public ContactNotFoundException(Long restaurantId) {
        super("Contact with restaurantId: '" + restaurantId + "' not found.");
    }
}
