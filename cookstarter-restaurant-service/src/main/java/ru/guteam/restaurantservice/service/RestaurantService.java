package ru.guteam.restaurantservice.service;

import ru.guteam.restaurantservice.dto.RestaurantDTO;
import ru.guteam.restaurantservice.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant getRestaurant(Long id);

    Long saveRestaurant(RestaurantDTO restaurant);

    List<Restaurant> getRestaurantsByName(String name);

    List<Restaurant> getAll();

    void updateRestaurant(Restaurant restaurant);

    void deleteRestaurant(Long id);
}
