package ru.guteam.customer_service.entities.utils;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel(description = "Class representing data about customer for his registration in the application.")
public class SystemCustomer {

    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's phone number", example = "89110002211", required = true, position = 1)
    private String username;

    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's password", example = "1000", required = true, position = 2)
    private String password;

    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's firstname", example = "Иван", required = true, position = 3)
    private String firstName;

    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's lastname", example = "Иванов", required = true, position = 4)
    private String lastName;

    @NotNull(message = "This field is required")
    @ApiModelProperty(notes = "User's email", example = "ivan@mail.ru", required = true, position = 5)
    private String email;
}
