package ru.guteam.restaurantservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.guteam.restaurantservice.dto.MenuDTO;
import ru.guteam.restaurantservice.exception.GetMenuException;
import ru.guteam.restaurantservice.model.Dish;
import ru.guteam.restaurantservice.repo.DishRepo;
import ru.guteam.restaurantservice.service.MenuService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final DishRepo dishRepo;

    @Override
    @Transactional
    public void createMenu(MenuDTO menu) {
        dishRepo.saveAll(menu.getDishes());
    }

    @Override
    @Transactional
    public List<Dish> getMenu(Long restaurantId) {
        return dishRepo.findAllByRestaurantId(restaurantId)
                .orElseThrow(() -> new GetMenuException(restaurantId));
    }

    @Override
    @Transactional
    public void deleteMenuByRestaurantId(Long restaurantId) {
        dishRepo.deleteAllByRestaurantId(restaurantId);
    }
}
