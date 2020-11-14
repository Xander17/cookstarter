package ru.guteam.restaurantservice.service;

import ru.guteam.restaurantservice.dto.MenuDTO;
import ru.guteam.restaurantservice.model.Dish;

import java.util.List;

public interface MenuService {
    void createMenu(MenuDTO menu);

    List<Dish> getMenu(Long restaurantId);

    void deleteMenuByRestaurantId(Long restaurantId);

}
