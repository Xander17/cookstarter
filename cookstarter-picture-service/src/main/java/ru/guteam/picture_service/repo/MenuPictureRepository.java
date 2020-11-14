package ru.guteam.picture_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guteam.picture_service.model.MenuPicture;

public interface MenuPictureRepository extends JpaRepository<MenuPicture, Long> {
}
