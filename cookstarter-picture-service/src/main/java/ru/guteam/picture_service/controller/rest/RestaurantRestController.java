package ru.guteam.picture_service.controller.rest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.guteam.cookstarter.api.dto.StatusResponse;
import ru.guteam.picture_service.controller.util.StatusResponseBuilder;
import ru.guteam.picture_service.service.RestaurantPictureService;

import java.io.IOException;

import static ru.guteam.cookstarter.api.dto.RequestMessageHeaders.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/picture/restaurant/api")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final RestaurantPictureService restaurantPictureService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StatusResponse> addPicture(@RequestParam("file") MultipartFile file,
                                                     @RequestHeader(JWT_TOKEN_HEADER) String token) throws IOException {
        Long id = restaurantPictureService.insert(file);
        return StatusResponseBuilder.okWithId(String.valueOf(id));
    }

    @PostMapping(value = "/update/{pictureId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StatusResponse> updatePicture(@RequestParam("file") MultipartFile file,
                                                        @RequestHeader(JWT_TOKEN_HEADER) String token,
                                                        @NonNull @PathVariable("pictureId") Long pictureId) throws IOException {
        restaurantPictureService.update(file, pictureId);
        return StatusResponseBuilder.ok();
    }

    @GetMapping("/delete/{pictureId}")
    public ResponseEntity<StatusResponse> deletePicture(@NonNull @PathVariable("pictureId") Long pictureId,
                                                        @RequestHeader(JWT_TOKEN_HEADER) String token) throws IOException {
        restaurantPictureService.deletePicture(pictureId);
        return StatusResponseBuilder.ok();
    }
}
