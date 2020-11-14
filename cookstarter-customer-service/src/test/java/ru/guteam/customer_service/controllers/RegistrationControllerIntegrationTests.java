package ru.guteam.customer_service.controllers;
// https://sysout.ru/testirovanie-spring-boot-prilozheniya-s-testresttemplate/

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.guteam.customer_service.entities.utils.SystemCustomer;
import ru.guteam.customer_service.entities.utils.SystemRestaurant;
import ru.guteam.customer_service.entities.utils.validation.ValidationErrorDTO;
import ru.guteam.customer_service.util.TestUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TestUtils utils;


    @Test
    public void whenCustomerRegistered_thenStatus200() throws IOException, URISyntaxException {
        SystemCustomer customer = utils.readJson("customer.json", SystemCustomer.class);
        ResponseEntity<String> response = restTemplate.postForEntity("/reg/customer", new HttpEntity<>(customer), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assert response.getBody() == null;
        assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    public void whenCustomerWithNull_thenStatus200AndErrors() throws IOException, URISyntaxException {
        SystemCustomer customer = utils.readJson("customerWithNull.json", SystemCustomer.class);
        ResponseEntity<ValidationErrorDTO> response = restTemplate.postForEntity("/reg/customer", new HttpEntity<>(customer), ValidationErrorDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assert response.getBody() != null;
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody(), instanceOf(ValidationErrorDTO.class));
    }

    @Test
    public void whenCustomersUsernameAlreadyExists_thenStatus409() throws IOException, URISyntaxException {
        SystemCustomer customer = utils.readJson("customerWithConflict.json", SystemCustomer.class);
        ResponseEntity<String> response = restTemplate.postForEntity("/reg/customer", new HttpEntity<>(customer), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
        assert response.getBody() != null;
        assertThat(response.getBody(), is(notNullValue()));
    }

    @Test
    public void whenRestaurantRegistered_thenStatus200() throws IOException, URISyntaxException {
        SystemRestaurant restaurant = utils.readJson("restaurant.json", SystemRestaurant.class);
        ResponseEntity<String> response = restTemplate.postForEntity("/reg/restaurant", new HttpEntity<>(restaurant), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assert response.getBody() == null;
        assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    public void whenRestaurantWithNull_thenStatus200AndErrors() throws IOException, URISyntaxException {
        SystemRestaurant restaurant = utils.readJson("restaurantWithNull.json", SystemRestaurant.class);
        ResponseEntity<ValidationErrorDTO> response = restTemplate.postForEntity("/reg/restaurant", new HttpEntity<>(restaurant), ValidationErrorDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assert response.getBody() != null;
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody(), instanceOf(ValidationErrorDTO.class));
    }

    @Test
    public void whenRestaurantsUsernameAlreadyExists_thenStatus409() throws IOException, URISyntaxException {
        SystemRestaurant restaurant = utils.readJson("restaurantWithConflict.json", SystemRestaurant.class);
        ResponseEntity<String> response = restTemplate.postForEntity("/reg/restaurant", new HttpEntity<>(restaurant), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
        assert response.getBody() != null;
        assertThat(response.getBody(), is(notNullValue()));
    }

}
