package ru.guteam.restaurantservice.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.guteam.restaurantservice.dto.ContactDTO;
import ru.guteam.restaurantservice.dto.RestaurantDTO;
import ru.guteam.restaurantservice.model.Contact;
import ru.guteam.restaurantservice.model.Restaurant;

@Component
@AllArgsConstructor
public class Mapper {
    private final ModelMapper modelMapper;

    public Restaurant mapToRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        modelMapper.map(restaurantDTO, restaurant);
        return restaurant;
    }

    public Contact mapToContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        modelMapper.map(contactDTO, contact);
        return contact;
    }

    public ContactDTO mapToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        modelMapper.map(contact, contactDTO);
        return contactDTO;
    }

}
