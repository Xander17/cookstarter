package ru.guteam.cookstarter.api.dto.customerservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
