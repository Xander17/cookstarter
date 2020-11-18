package ru.guteam.cookstarter.api.dto.orderservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.guteam.cookstarter.api.enums.OrderStatus;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
    @NotNull
    private Long id;
    @NotNull
    private OrderStatus status;
}
