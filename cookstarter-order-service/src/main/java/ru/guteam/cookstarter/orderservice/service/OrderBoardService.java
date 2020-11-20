package ru.guteam.cookstarter.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.guteam.cookstarter.api.dto.orderservice.OrderBoardDto;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderBoardService {

    private final RestTemplate restTemplate;

    @Value("${app.order-board.path}${app.order-board.add}")
    private String orderBoardAddPath;

    public void sendOrder(OrderBoardDto order, String token) {
        log.info("Заказ отправляется в ресторан:\n" + order);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Authorization", List.of(token));
        RequestEntity<OrderBoardDto> requestEntity = new RequestEntity<>(order, httpHeaders, HttpMethod.POST, URI.create(orderBoardAddPath));
        restTemplate.exchange(requestEntity, Void.class);
    }
}
