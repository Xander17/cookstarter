package ru.guteam.cookstarter.orderservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.guteam.cookstarter.api.dto.orderservice.OrderBoardDto;
import ru.guteam.cookstarter.api.dto.orderservice.OrderDto;
import ru.guteam.cookstarter.api.enums.OrderStatus;
import ru.guteam.cookstarter.orderservice.exception.OrderProcessingException;
import ru.guteam.cookstarter.orderservice.service.*;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusHandler {

    private final OrderService orderService;
    private final OrderBoardService orderBoardService;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final JwtService jwtService;

    public void handle(Long id, OrderStatus status) {
        OrderDto order = orderService.getById(id);
        switch (status) {
            case PAID:
                log.info(String.format("Устанавливается статус %s заказу id=%s", status.name(), id));
                if (order.getStatus() != OrderStatus.SAVED) {
                    throw new OrderProcessingException("Статус PAID можно установить только заказу со статусом SAVED");
                }
                String token = jwtService.generateInnerToken();
                orderService.setStatus(id, status);
                orderBoardService.sendOrder(OrderBoardDto.builder()
                        .orderId(id)
                        .restaurantId(order.getRestaurantId())
                        .userName(customerService.getUserInfo(order.getCustomerId(), token).getFullName())
                        .dishes(order.getDishes().entrySet().stream()
                                .map(entry -> OrderBoardDto.Dish.builder()
                                        .name(restaurantService.getDishName(entry.getKey(), token))
                                        .price(entry.getValue().getPrice())
                                        .quantity(entry.getValue().getQuantity())
                                        .build())
                                .collect(Collectors.toList()))
                        .build(),
                        token);
            default:
        }
    }
}
