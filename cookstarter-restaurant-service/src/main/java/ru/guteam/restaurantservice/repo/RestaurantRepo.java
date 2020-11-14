package ru.guteam.restaurantservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guteam.restaurantservice.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);

    Optional<List<Restaurant>> findByNameContains(String name);
}
