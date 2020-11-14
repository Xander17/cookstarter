package ru.guteam.customer_service.entities.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.guteam.customer_service.entities.Customer;
import ru.guteam.customer_service.entities.Role;
import ru.guteam.customer_service.entities.utils.enums.RolesTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Class representing data about restaurant for his registration in the application.")
public class SystemRestaurant {

    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "Unique identifier of the restaurant. No two restaurants can have the same id.", example = "1", required = true, position = 1)
    private Long id;
    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "Unique username", example = "89110002233", required = true, position = 2)
    private String username;
    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's password", example = "1000", required = true, position = 3)
    private String password;
    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's authorities", example = "RESTAURANT_MANAGER", required = true, position = 4)
    private RolesTypeEnum role;

}
