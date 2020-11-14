package ru.guteam.picture_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guteam.picture_service.model.AvatarPicture;

public interface AvatarPictureRepository extends JpaRepository<AvatarPicture, Long> {
}
