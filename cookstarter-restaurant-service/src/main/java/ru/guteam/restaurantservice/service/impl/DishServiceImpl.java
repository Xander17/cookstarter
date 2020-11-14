package ru.guteam.restaurantservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.guteam.restaurantservice.exception.DishNotFountException;
import ru.guteam.restaurantservice.model.Dish;
import ru.guteam.restaurantservice.repo.DishRepo;
import ru.guteam.restaurantservice.service.DishService;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepo dishRepo;


    @Override
    @Transactional

    public void saveDish(Dish dish) {
        dishRepo.save(dish);
    }

    @Override
    public Dish findById(Long id) {
        return dishRepo.findById(id).orElseThrow(() -> new DishNotFountException(id));
    }

    @Override
    @Transactional
    public void updateDish(Dish dish) {
        dishRepo.findById(dish.getId()).orElseThrow(() -> new DishNotFountException(dish.getId()));
        dishRepo.save(dish);
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        dishRepo.findById(id).orElseThrow(() -> new DishNotFountException(id));
        dishRepo.deleteById(id);
    }
}
