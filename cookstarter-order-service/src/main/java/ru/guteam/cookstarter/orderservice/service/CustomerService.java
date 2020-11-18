package ru.guteam.cookstarter.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestTemplate restTemplate;

    @Value("${app.customer-service.path}${app.customer-service.get-info}")
    private String getCustomerPath;

    public String getUsername(Long id) {
        log.info("Запрос пользователя с id=" + id);
        // stub
        return "username id=" + id;

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.put("Authorization", List.of("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjM0NTAwMDcwLCJpYXQiOjE2MDI5NjQwNzB9.1HLjqDbZz5VN6B268zQA5CVCQ0maYmyaWcY6YOMoMow"));
//        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(String.format(getCustomerPath, id)));
//
//        DishDto dish = restTemplate.exchange(requestEntity, DishDto.class).getBody();
//        if (dish == null) {
//            throw new OrderProcessingException("Нет информации о блюде id=" + id);
//        }
//        return dish.getName();
    }
}
