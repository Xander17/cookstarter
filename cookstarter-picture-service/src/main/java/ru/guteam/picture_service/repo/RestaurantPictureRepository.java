package ru.guteam.picture_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guteam.picture_service.model.RestaurantPicture;

public interface RestaurantPictureRepository extends JpaRepository<RestaurantPicture, Long> {
}
