package ru.guteam.cookstarter.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.guteam.cookstarter.api.dto.restaurantservice.DishDto;
import ru.guteam.cookstarter.orderservice.exception.OrderProcessingException;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestTemplate restTemplate;

    @Value("${app.restaurant-service.path}${app.restaurant-service.get-dish}")
    private String getDishPath;

    public String getDishName(Long id) {
        log.info("Запрос блюда с id=" + id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Authorization", List.of("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjM0NTAwMDcwLCJpYXQiOjE2MDI5NjQwNzB9.1HLjqDbZz5VN6B268zQA5CVCQ0maYmyaWcY6YOMoMow"));
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(String.format(getDishPath, id)));

        DishDto dish = restTemplate.exchange(requestEntity, DishDto.class).getBody();
        if (dish == null) {
            throw new OrderProcessingException("Нет информации о блюде id=" + id);
        }
        return dish.getName();
    }
}
