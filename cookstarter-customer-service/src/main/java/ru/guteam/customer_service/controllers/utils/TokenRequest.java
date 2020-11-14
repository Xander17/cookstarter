package ru.guteam.customer_service.controllers.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing data about user for his authentication in the application.")
public class TokenRequest {
    @NotNull
    @ApiModelProperty(notes = "Unique username", example = "1000", required = true, position = 1)
    private String username;
    @NotNull
    @ApiModelProperty(notes = "User's password", example = "1000", required = true, position = 2)
    private String password;
}
