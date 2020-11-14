package ru.guteam.restaurantservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    private String description;
    private Long pictureId;
    @NotNull
    private Long restaurantId;
}
