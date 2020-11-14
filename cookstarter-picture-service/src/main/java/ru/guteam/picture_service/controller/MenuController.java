package ru.guteam.picture_service.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.guteam.picture_service.service.MenuPictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.guteam.cookstarter.api.dto.RequestMessageHeaders.JWT_TOKEN_HEADER;

@Controller
@RequestMapping("/picture/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuPictureService menuPictureService;

    @GetMapping("get/{pictureId}")
    public void getPictureById(@NonNull @PathVariable("pictureId") Long pictureId,
                               @RequestParam(JWT_TOKEN_HEADER) String token, HttpServletResponse response) throws IOException {
        menuPictureService.getPicture(pictureId, response);
    }
}
