package ru.guteam.restaurantservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    @NotNull
    private String name;
    @NotNull
    private String description;
    private Long pictureId;
}
