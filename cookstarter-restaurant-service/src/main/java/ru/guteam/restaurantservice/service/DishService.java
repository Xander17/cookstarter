package ru.guteam.restaurantservice.service;

import ru.guteam.restaurantservice.model.Dish;

public interface DishService {
    void saveDish(Dish dish);

    Dish findById(Long id);

    void updateDish(Dish dish);

    void deleteDish(Long id);
}
