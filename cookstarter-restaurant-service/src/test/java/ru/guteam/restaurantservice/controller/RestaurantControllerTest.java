package ru.guteam.restaurantservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.guteam.restaurantservice.dto.RestaurantDTO;
import ru.guteam.restaurantservice.model.Restaurant;
import ru.guteam.restaurantservice.service.ContactService;
import ru.guteam.restaurantservice.service.MenuService;
import ru.guteam.restaurantservice.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.guteam.restaurantservice.controller.util.Token.TOKEN;
import static ru.guteam.restaurantservice.controller.util.Url.*;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;


@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private ContactService contactService;
    @MockBean
    private MenuService menuService;

    @Test
    public void givenRestaurant_whenAdd_thenStatusOkAndRestaurantId() throws Exception {
        Long id = 1l;
        RestaurantDTO restaurant = new RestaurantDTO("Some Restaurant", "some discription", 123l);
        Mockito.when(restaurantService.saveRestaurant(restaurant)).thenReturn(id);

        mockMvc.perform(
                post(ADD_RESTAURANT)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(id)));
    }

    @Test
    public void givenId_whenGet_thenStatusOkAndRestaurant() throws Exception {
        Long id = 1l;
        Restaurant restaurant = new Restaurant(1l, "Some Restaurant", "some discription", 123l);
        Mockito.when(restaurantService.getRestaurant(id)).thenReturn(restaurant);

        mockMvc.perform(
                get(GET_RESTAURANT_BY_ID + id)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(restaurant)));
    }


    @Test
    public void givenName_whenGet_thenStatusOkAndRestaurantsList() throws Exception {
        String name = "Name";
        Restaurant restaurant1 = new Restaurant(1l, "Some Restaurant 1", "some discription 1", 123l);
        Restaurant restaurant2 = new Restaurant(2l, "Some Restaurant 2", "some discription 2", 321l);
        List restaurants = new ArrayList();
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        Mockito.when(restaurantService.getRestaurantsByName(name)).thenReturn(restaurants);

        mockMvc.perform(
                get(GET_RESTAURANT_BY_NAME + name)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(restaurants)));
    }


    @Test
    public void requestALL_whenGet_thenStatusOkAndRestaurantsList() throws Exception {
        Restaurant restaurant1 = new Restaurant(1l, "Some Restaurant 1", "some discription 1", 123l);
        Restaurant restaurant2 = new Restaurant(2l, "Some Restaurant 2", "some discription 2", 321l);
        List restaurants = new ArrayList();
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        Mockito.when(restaurantService.getAll()).thenReturn(restaurants);

        mockMvc.perform(
                get(GET_ALL_RESTAURANTS)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(restaurants)));
    }

    @Test
    public void givenRestaurant_whenUpdate_thenStatusOk() throws Exception {
        Restaurant restaurant = new Restaurant(1l, "Some Restaurant 1", "some discription 1", 123l);

        mockMvc.perform(
                post(UPDATE_RESTAURANT)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void givenId_whenDelete_thenStatusOkAndId() throws Exception {
        Long id = 1l;

        mockMvc.perform(
                get(DELETE_RESTAURANT + id)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(id)));
    }

}
