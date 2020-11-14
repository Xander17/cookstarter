package ru.guteam.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.guteam.bot.rest.RestClient;
import ru.guteam.bot.sender.OrderSender;

@Service
public class OrderService {
    private OrderSender orderSender;
    private RestClient restClient;

    @Autowired
    public OrderService(OrderSender orderSender, RestClient restClient) {
        this.orderSender = orderSender;
        this.restClient = restClient;
    }
}
