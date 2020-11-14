package ru.guteam.restaurantservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.guteam.restaurantservice.model.Dish;
import ru.guteam.restaurantservice.service.DishService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.guteam.restaurantservice.controller.util.Token.TOKEN;
import static ru.guteam.restaurantservice.controller.util.Url.*;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;

@WebMvcTest(DishController.class)
public class DishControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DishService dishService;


    @Test
    public void givenDish_whenAdd_thenStatusOk() throws Exception {
        Dish dish = new Dish();

        mockMvc.perform(
                post(ADD_DISH)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenDish_whenUpdate_thenStatusOk() throws Exception {
        Dish dish = new Dish();

        mockMvc.perform(
                post(UPDATE_DISH)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenDish_whenDelete_thenStatusOk() throws Exception {
        Dish dish = new Dish();

        mockMvc.perform(
                get(DELETE_DISH)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
