package ru.guteam.customer_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guteam.customer_service.entities.Customer;
import ru.guteam.customer_service.entities.dtos.CustomerDTO;
import ru.guteam.customer_service.services.CustomersService;

import java.util.Optional;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Api("Set of endpoints for users information")
public class UsersInfoController {
    private final CustomersService customersService;


    @ApiOperation("Returns firstname and lastname for the customer by his id.")
    @GetMapping(value = "customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomersInfo(@PathVariable @ApiParam("Cannot be empty") Long customerId) {
        Optional<Customer> customer = customersService.findById(customerId);
        if (!customer.isPresent()) {
            return new ResponseEntity<>("Customer with id: " + customerId + " is not found", HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(new CustomerDTO(customer.get()), HttpStatus.OK);
    }

}
