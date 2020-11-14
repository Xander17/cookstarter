package ru.guteam.restaurantservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.guteam.restaurantservice.model.Dish;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    @NotNull
    private List<Dish> dishes;
}
