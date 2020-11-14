package ru.guteam.restaurantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.guteam.restaurantservice.model.Dish;
import ru.guteam.restaurantservice.service.DishService;

import static org.springframework.http.HttpStatus.OK;
import static ru.guteam.restaurantservice.controller.util.ResponseBuilder.dishAndStatus;
import static ru.guteam.restaurantservice.controller.util.ResponseBuilder.status;
import static ru.guteam.restaurantservice.util.RequestHeaders.JWT_HEADER;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addDish(@RequestHeader(JWT_HEADER) String token,
                                              @RequestBody Dish dish) {
        dishService.saveDish(dish);
        return status(OK);
    }

    @CrossOrigin
    @GetMapping("/get/{id}")
    public ResponseEntity<Dish> getDish(@RequestHeader(JWT_HEADER) String token,
                                        @PathVariable Long id) {
        Dish dish = dishService.findById(id);
        return dishAndStatus(dish, OK);
    }

    @CrossOrigin
    @PostMapping("/update")
    public ResponseEntity<HttpStatus> updateDish(@RequestHeader(JWT_HEADER) String token,
                                                 @RequestBody Dish dish) {
        dishService.updateDish(dish);
        return status(OK);
    }

    @CrossOrigin
    @GetMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@RequestHeader(JWT_HEADER) String token,
                                                 @PathVariable Long id) {
        dishService.deleteDish(id);
        return status(OK);
    }

}
