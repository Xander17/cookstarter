package ru.guteam.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guteam.restaurantservice.dto.MenuDTO;
import ru.guteam.restaurantservice.model.Dish;
import ru.guteam.restaurantservice.service.MenuService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static ru.guteam.restaurantservice.controller.util.ResponseBuilder.listAndStatus;
import static ru.guteam.restaurantservice.controller.util.ResponseBuilder.status;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMenu(@RequestHeader(JWT_HEADER) String token,
                                              @RequestBody MenuDTO menu) {
        menuService.createMenu(menu);
        return status(OK);
    }

    @CrossOrigin
    @GetMapping("/get/{restaurantId}")
    public ResponseEntity<List> getMenu(@RequestHeader(JWT_HEADER) String token,
                                        @PathVariable Long restaurantId) {
        List<Dish> menu = menuService.getMenu(restaurantId);
        return listAndStatus(menu, OK);
    }


}
