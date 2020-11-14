package ru.guteam.restaurantservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.guteam.restaurantservice.dto.ContactDTO;
import ru.guteam.restaurantservice.service.ContactService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.guteam.restaurantservice.controller.util.Token.TOKEN;
import static ru.guteam.restaurantservice.controller.util.Url.*;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ContactService contactService;

    @Test
    public void givenContact_whenAdd_thenStatusOk() throws Exception {
        ContactDTO contact = new ContactDTO();

        mockMvc.perform(
                post(ADD_CONTACT)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(contact))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRestaurantId_thenStatusOkAndContact() throws Exception {
        Long restaurantId = 1l;
        ContactDTO contact = new ContactDTO();

        Mockito.when(contactService.getContactByRestaurantId(Mockito.anyLong())).thenReturn(contact);

        mockMvc.perform(
                get(GET_CONTACT_BY_RESTAURANT_ID + restaurantId)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(contact)));
    }

    @Test
    public void givenContact_whenUpdate_thenStatusOk() throws Exception {
        ContactDTO contact = new ContactDTO();

        mockMvc.perform(
                post(UPDATE_CONTACT)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN))
                        .content(objectMapper.writeValueAsString(contact))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRestaurantId_whenDelete_thenStatusOk() throws Exception {
        Long restaurantId = 1l;

        mockMvc.perform(
                get(DELETE_CONTACT_BY_RESTAURANT_ID + restaurantId)
                        .header(JWT_HEADER, objectMapper.writeValueAsString(TOKEN)))
                .andExpect(status().isOk());
    }

}
