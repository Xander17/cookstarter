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
@ApiModel(description = "Class representing response with a JWT token and restaurant's or customer's id in the application.")
public class TokenResponse {
    @NotNull
    @ApiModelProperty(notes = "Unique token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAiLCJyb2xlIjpbIkNVU1RPTUVSIl0sImV4cCI6MTYwMzM4MDUxNywiaWF0IjoxNjAzMzc2OTE3fQ.FCNPs5fKYSqVihxLyFzeriDm7MfaSEynHNpz0AVXN9s",
            required = true, position = 1)
    private String token;
    @NotNull
    @ApiModelProperty(notes = "Restaurant's or customer's unique identifier. No two restaurants can have the same id. " +
            "No two customers can have the same id. But a customer and a restaurant can have the same id", example = "1", required = true, position = 2)
    private Long id;

}
