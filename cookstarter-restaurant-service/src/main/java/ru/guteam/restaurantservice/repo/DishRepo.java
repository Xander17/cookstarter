package ru.guteam.restaurantservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guteam.restaurantservice.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRepo extends JpaRepository<Dish, Long> {

    Optional<List<Dish>> findAllByRestaurantId(Long restaurantId);

    void deleteAllByRestaurantId(Long restaurantId);
}
