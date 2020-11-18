package ru.guteam.cookstarter.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.guteam.cookstarter.api.dto.orderservice.OrderBoardDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderBoardService {

    private final RestTemplate restTemplate;

    @Value("${app.order-board.path}${app.order-board.add}")
    private String orderBoardAddPath;

    public void sendOrder(OrderBoardDto order) {
        log.info("Заказ отправляется в ресторан:\n" + order);
        restTemplate.postForObject(orderBoardAddPath, order, Void.class);
    }
}
