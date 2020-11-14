package ru.guteam.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.guteam.bot.rest.RestClient;
import ru.guteam.bot.sender.RestaurantSender;

@Service
public class RestaurantService {
    private RestaurantSender restaurantSender;
    private RestClient restClient;

    @Autowired
    public RestaurantService(RestaurantSender restaurantSender, RestClient restClient) {
        this.restaurantSender = restaurantSender;
        this.restClient = restClient;
    }
}
