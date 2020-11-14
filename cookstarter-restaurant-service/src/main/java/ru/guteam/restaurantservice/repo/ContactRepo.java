package ru.guteam.restaurantservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guteam.restaurantservice.model.Contact;

import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact, Long> {
    Optional<Contact> findByRestaurantId(Long restaurant_id);
    void deleteByRestaurantId(Long restaurant_id);
}
