package ru.guteam.cookstarter.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.guteam.cookstarter.api.dto.customerservice.UserInfoDto;
import ru.guteam.cookstarter.orderservice.exception.OrderProcessingException;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final RestTemplate restTemplate;

    @Value("${app.customer-service.path}${app.customer-service.get-info}")
    private String getCustomerInfoPath;

    public UserInfoDto getUserInfo(Long id, String token) {
        log.info("Запрос пользователя с id=" + id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Authorization", List.of(token));
        RequestEntity<Void> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, URI.create(String.format(getCustomerInfoPath, id)));

        UserInfoDto info = restTemplate.exchange(requestEntity, UserInfoDto.class).getBody();
        if (info == null) {
            throw new OrderProcessingException("Нет информации о заказчике id=" + id);
        }
        return info;
    }
}
