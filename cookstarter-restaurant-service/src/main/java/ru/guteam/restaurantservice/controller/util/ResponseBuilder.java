package ru.guteam.restaurantservice.controller.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.guteam.restaurantservice.dto.ContactDTO;
import ru.guteam.restaurantservice.dto.MessageDTO;
import ru.guteam.restaurantservice.model.Contact;
import ru.guteam.restaurantservice.model.Restaurant;

import java.util.List;

@UtilityClass
public class ResponseBuilder {

    public ResponseEntity<Long> idAndStatus(Long id, HttpStatus status) {
        return ResponseEntity.status(status).body(id);
    }

    public ResponseEntity<List> listAndStatus(List list, HttpStatus status) {
        return ResponseEntity.status(status).body(list);
    }

    public ResponseEntity<Restaurant> restaurantAndStatus(Restaurant restaurant, HttpStatus status) {
        return ResponseEntity.status(status).body(restaurant);
    }

    public ResponseEntity<Dish> dishAndStatus(Dish dish, HttpStatus status) {
        return ResponseEntity.status(status).body(dish);
    }

    public ResponseEntity<ContactDTO> contactAndStatus(ContactDTO contact, HttpStatus status) {
        return ResponseEntity.status(status).body(contact);
    }

    public ResponseEntity<HttpStatus> status(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }

    public ResponseEntity<MessageDTO> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new MessageDTO(message));
    }

    public ResponseEntity<String> error(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }


}
