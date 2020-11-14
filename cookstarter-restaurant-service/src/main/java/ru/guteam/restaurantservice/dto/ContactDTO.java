package ru.guteam.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @NotNull
    private String location;
    @NotNull
    private String mail;
    @NotNull
    private String website;
    @NotNull
    private Long restaurantId;
}
