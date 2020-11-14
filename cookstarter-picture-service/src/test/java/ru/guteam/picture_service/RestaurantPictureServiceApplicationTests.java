package ru.guteam.picture_service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import ru.guteam.picture_service.model.RestaurantPicture;
import ru.guteam.picture_service.repo.RestaurantPictureRepository;
import ru.guteam.picture_service.service.RestaurantPictureService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.guteam.cookstarter.api.dto.RequestMessageHeaders.JWT_TOKEN_HEADER;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class RestaurantPictureServiceApplicationTests {
    private static final String GET_PICTURE_URL = "/picture/restaurant/get/{pictureId}";
    private static final String ADD_PICTUTE_URL = "/picture/restaurant/api/add";
    private static final String UPDATE_PICTURE_URL = "/picture/restaurant/api/update/{pictureId}";
    private static final String DELETE_PICTURE_URL = "/picture/restaurant/api/delete/{pictureId}";

    @Autowired
    private RestaurantPictureService restaurantPictureService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RestaurantPictureRepository restaurantPictureRepository;

    @Value("${app.auth-type}")
    private String authType;

    @Value("${app.path-directory-test}")
    private String pathPic;
    private MultipartFile file;
    private String token;
    private Long id;


    @PostConstruct
    public void setFile() throws IOException {
        token = authType + " eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjM0NTAwMDcwLCJpYXQiOjE2MDI5NjQwNzB9.1HLjqDbZz5VN6B268zQA5CVCQ0maYmyaWcY6YOMoMow";
        Path testPic = Paths.get(pathPic,"5mm.jpg");
        file = new MockMultipartFile("file","5mm.jpg","image",Files.readAllBytes(testPic.toAbsolutePath()));
    }

    @AfterEach
    public void resetDb() throws IOException {
        Optional<List<RestaurantPicture>> pictures = Optional.of(restaurantPictureRepository.findAll());
        for (RestaurantPicture p: pictures.get()) {
            Path image = Paths.get(p.getPath());
            Files.delete(image);
            restaurantPictureRepository.deleteById(p.getId());
        }
    }

    @Test
    public void getPicture() throws Exception {
        id = restaurantPictureService.insert(file);
        mockMvc.perform(
                get(GET_PICTURE_URL,id)
                        .param(JWT_TOKEN_HEADER, token))
                .andExpect(status().isOk());
    }

    @Test
    public void addPicture() throws Exception {
        mockMvc.perform(
                multipart(ADD_PICTUTE_URL)
                        .file((MockMultipartFile) file)
                        .header(JWT_TOKEN_HEADER, token))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePicture() throws Exception {
        id = restaurantPictureService.insert(file);
        mockMvc.perform(
                multipart(UPDATE_PICTURE_URL, id)
                        .file((MockMultipartFile) file)
                        .header(JWT_TOKEN_HEADER, token))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePicture() throws Exception {
        id = restaurantPictureService.insert(file);
        mockMvc.perform(
                get(DELETE_PICTURE_URL,id)
                        .header(JWT_TOKEN_HEADER, token))
                .andExpect(status().isOk());
    }
}
