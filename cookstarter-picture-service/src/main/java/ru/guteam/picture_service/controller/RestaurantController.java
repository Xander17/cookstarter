package ru.guteam.picture_service.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.guteam.picture_service.service.RestaurantPictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.guteam.cookstarter.api.dto.RequestMessageHeaders.JWT_TOKEN_HEADER;

@Controller
@RequestMapping("/picture/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantPictureService restaurantPictureService;

    @GetMapping("get/{pictureId}")
    public void getPictureById(@NonNull @PathVariable("pictureId") Long pictureId,
                                @RequestParam(JWT_TOKEN_HEADER) String token, HttpServletResponse response) throws IOException {
        restaurantPictureService.getPicture(pictureId, response);
    }
}
