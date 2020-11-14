package ru.guteam.picture_service.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.guteam.picture_service.exception.NotPictureException;
import ru.guteam.picture_service.model.AvatarPicture;
import ru.guteam.picture_service.repo.AvatarPictureRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class AvatarPictureService {
    @Value("${app.name-length}")
    private int nameLength;
    @Value("${app.path-directory-avatar}")
    private String rootPath;
    private final AvatarPictureRepository avatarPictureRepository;

    @Autowired
    public AvatarPictureService(AvatarPictureRepository avatarPictureRepository) {
        this.avatarPictureRepository = avatarPictureRepository;
    }

    public void getPicture(Long id, HttpServletResponse response) throws IOException {
        Optional<AvatarPicture> picture = avatarPictureRepository.findById(id);
        if (picture.isPresent()) {
            Path image = Paths.get(picture.get().getPath());
            byte[] bytes = Files.readAllBytes(image);
            response.setContentType("image");
            response.getOutputStream().write(bytes);
        } else {
            throw new NotPictureException("Картинка не найдена");
        }
    }

    @Transactional
    public void deletePicture(Long id) throws IOException {
        Optional<AvatarPicture> oldFile = avatarPictureRepository.findById(id);
        if (oldFile.isPresent()) {
            Path oldPic = Paths.get(oldFile.get().getPath());
            Files.delete(oldPic);
            avatarPictureRepository.deleteById(id);
        } else {
            throw new NotPictureException("Картинка не найдена");
        }
    }

    @Transactional
    public Long insert(MultipartFile multipartFile) throws IOException {
        Path root = Paths.get(rootPath);
        String name = RandomStringUtils.randomAlphanumeric(nameLength) + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        Path picFile = Paths.get(root.toString(), name);
        Files.copy(multipartFile.getInputStream(), root.resolve(Objects.requireNonNull(name)));
        AvatarPicture picture = AvatarPicture.builder()
                .path(picFile.toAbsolutePath().toString())
                .build();
        avatarPictureRepository.save(picture);
        return picture.getId();
    }

    @Transactional
    public void update(MultipartFile multipartFile, Long pictureId) throws IOException {
        Path root = Paths.get(rootPath);
        String name = RandomStringUtils.randomAlphanumeric(nameLength) + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        Path picFile = Paths.get(root.toString(), name);
        Optional<AvatarPicture> oldFile = avatarPictureRepository.findById(pictureId);
        if (oldFile.isPresent()) {
            Path oldPic = Paths.get(oldFile.get().getPath());
            Files.delete(oldPic);
            Files.copy(multipartFile.getInputStream(), root.resolve(Objects.requireNonNull(name)));
            AvatarPicture picture = AvatarPicture.builder()
                    .id(pictureId)
                    .path(picFile.toAbsolutePath().toString())
                    .build();
            avatarPictureRepository.save(picture);
        } else {
            throw new NotPictureException("Картинка не найдена");
        }
    }
}
