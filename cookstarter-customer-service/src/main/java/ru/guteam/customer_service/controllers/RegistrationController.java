package ru.guteam.customer_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.guteam.customer_service.entities.utils.SystemCustomer;
import ru.guteam.customer_service.entities.utils.SystemRestaurant;
import ru.guteam.customer_service.entities.utils.validation.ValidationErrorDTO;
import ru.guteam.customer_service.services.CustomersService;
import ru.guteam.customer_service.services.UsersService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/reg")
@AllArgsConstructor
@Api("Set of endpoints for registration")
public class RegistrationController {
    private final UsersService usersService;
    private final CustomersService customersService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @ApiOperation("Returns HttpStatus of trying registration procedure for restaurants. Inside the object of SystemRestaurant type is data about it.")
    @PostMapping(value = "/restaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restaurantRegistration(@RequestBody @ApiParam("Cannot be empty") @Valid SystemRestaurant systemRestaurant) {
        String username = systemRestaurant.getUsername();
        if (usersService.existsByUsername(username)) {
            return new ResponseEntity<>("Ресторан с логином: " + username + " уже существует", HttpStatus.CONFLICT);
        }
        usersService.saveRestaurant(systemRestaurant);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Returns HttpStatus of trying registration procedure for customers. Inside the object of SystemCustomer type is data about it.")
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> customerRegistration(@RequestBody @ApiParam("Cannot be empty") @Valid SystemCustomer systemCustomer) {
        String username = systemCustomer.getUsername();
        if (usersService.existsByUsername(username)) {
            return new ResponseEntity<>("Клиент с логином: " + username + " уже существует", HttpStatus.CONFLICT);
        }
        String email = systemCustomer.getEmail();
        if (customersService.existsByEmail(email)) {
            return new ResponseEntity<>("Клиент с адресом: " + email + " уже существует", HttpStatus.CONFLICT);
        }
        customersService.saveBySystemCustomer(systemCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();
        for (FieldError fieldError : fieldErrors) {
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
            log.info("Ошибка при заполнении поля " + fieldError.getField() +
                    ": " + fieldError.getDefaultMessage());
        }
        return dto;
    }

}
