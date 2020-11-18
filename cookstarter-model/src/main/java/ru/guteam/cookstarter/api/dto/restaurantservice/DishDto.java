package ru.guteam.cookstarter.api.dto.restaurantservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Long pictureId;
    private Long restaurantId;
}
