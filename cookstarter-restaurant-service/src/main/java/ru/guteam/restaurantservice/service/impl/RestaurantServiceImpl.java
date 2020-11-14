package ru.guteam.restaurantservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.guteam.restaurantservice.dto.RestaurantDTO;
import ru.guteam.restaurantservice.exception.RestaurantNotFoundException;
import ru.guteam.restaurantservice.model.Restaurant;
import ru.guteam.restaurantservice.repo.RestaurantRepo;
import ru.guteam.restaurantservice.service.RestaurantService;
import ru.guteam.restaurantservice.util.Mapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepo restaurantRepo;
    private final Mapper mapper;

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantRepo.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Override
    @Transactional
    public Long saveRestaurant(RestaurantDTO restaurant) {
        restaurantRepo.save(mapper.mapToRestaurant(restaurant));
        Long id = restaurantRepo.findByName(restaurant.getName())
                .orElseThrow(() -> new RestaurantNotFoundException(restaurant.getName())).getId();
        return id;
    }

    @Override
    @Transactional
    public List<Restaurant> getRestaurantsByName(String name) {
        List<Restaurant> restaurants = restaurantRepo.findByNameContains(name)
                .orElseThrow(() -> new RestaurantNotFoundException(name));
        return restaurants;
    }

    @Override
    @Transactional
    public List<Restaurant> getAll() {
        return restaurantRepo.findAll();
    }

    @Override
    @Transactional
    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepo.save(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepo.deleteById(id);
    }
}
