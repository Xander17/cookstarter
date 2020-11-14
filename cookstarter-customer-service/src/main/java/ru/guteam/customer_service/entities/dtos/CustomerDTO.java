package ru.guteam.customer_service.entities.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import ru.guteam.customer_service.entities.Customer;
import ru.guteam.customer_service.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ApiModel(description = "Class representing a data about customer.")
public class CustomerDTO {

    @NotNull
    @ApiModelProperty(notes = "Customers firstname", example = "Ivan", required = true, position = 1)
    private String firstName;

    @NotNull
    @ApiModelProperty(notes = "Customers lastname", example = "Ivanov", required = true, position = 2)
    private String lastName;

    public CustomerDTO(Customer customer) {
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
    }
}
