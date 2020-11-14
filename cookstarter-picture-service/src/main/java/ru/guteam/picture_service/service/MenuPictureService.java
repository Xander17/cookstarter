package ru.guteam.picture_service.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.guteam.picture_service.exception.NotPictureException;
import ru.guteam.picture_service.model.MenuPicture;
import ru.guteam.picture_service.repo.MenuPictureRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuPictureService {
    @Value("${app.name-length}")
    private int nameLength;
    @Value("${app.path-directory-menu}")
    private String rootPath;
    private final MenuPictureRepository menuPictureRepository;

    @Autowired
    public MenuPictureService(MenuPictureRepository menuPictureRepository) {
        this.menuPictureRepository = menuPictureRepository;
    }

    public void getPicture(Long id, HttpServletResponse response) throws IOException {
        Optional<MenuPicture> picture = menuPictureRepository.findById(id);
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
        Optional<MenuPicture> oldFile = menuPictureRepository.findById(id);
        if (oldFile.isPresent()) {
            Path oldPic = Paths.get(oldFile.get().getPath());
            Files.delete(oldPic);
            menuPictureRepository.deleteById(id);
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
        MenuPicture picture = MenuPicture.builder()
                .path(picFile.toAbsolutePath().toString())
                .build();
        menuPictureRepository.save(picture);
        return picture.getId();
    }

    @Transactional
    public void update(MultipartFile multipartFile, Long pictureId) throws IOException {
        Path root = Paths.get(rootPath);
        String name = RandomStringUtils.randomAlphanumeric(nameLength) + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        Path picFile = Paths.get(root.toString(), name);
        Optional<MenuPicture> oldFile = menuPictureRepository.findById(pictureId);
        if (oldFile.isPresent()) {
            Path oldPic = Paths.get(oldFile.get().getPath());
            Files.delete(oldPic);
            Files.copy(multipartFile.getInputStream(), root.resolve(Objects.requireNonNull(name)));
            MenuPicture picture = MenuPicture.builder()
                    .id(pictureId)
                    .path(picFile.toAbsolutePath().toString())
                    .build();
            menuPictureRepository.save(picture);
        } else {
            throw new NotPictureException("Картинка не найдена");
        }
    }
}
