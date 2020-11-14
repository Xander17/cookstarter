package ru.guteam.restaurantservice.service;

import ru.guteam.restaurantservice.dto.ContactDTO;

public interface ContactService {
    void saveContact(ContactDTO contact);

    ContactDTO getContactByRestaurantId(Long restaurant_id);

    void updateContact(ContactDTO contact);

    void deleteContactByRestaurantId(Long restaurant_id);

}
