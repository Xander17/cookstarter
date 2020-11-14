package ru.guteam.restaurantservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.guteam.restaurantservice.dto.MenuDTO;
import ru.guteam.restaurantservice.model.Dish;
import ru.guteam.restaurantservice.service.MenuService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.guteam.restaurantservice.controller.util.Token.TOKEN;
import static ru.guteam.restaurantservice.controller.util.Url.ADD_MENU;
import static ru.guteam.restaurantservice.controller.util.Url.GET_MENU;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;

@WebMvcTest(MenuController.class)
public class MenuControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MenuService menuService;

    @Test
    public void givenDish_whenAdd_thenStatusOk() throws Exception {
        MenuDTO menu = new MenuDTO();

        mockMvc.perform(
                post(ADD_MENU)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(menu))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRestaurantId_whenGet_thenStatusOkAndList() throws Exception {
        Long restaurantId = anyLong();
        List menu = new ArrayList();
        menu.add(new Dish());
        menu.add(new Dish());

        Mockito.when(menuService.getMenu(restaurantId)).thenReturn(menu);

        mockMvc.perform(
                get(GET_MENU + restaurantId)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(menu)));
    }

}
