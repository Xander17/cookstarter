package ru.guteam.customer_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;

@Component
@RequiredArgsConstructor
public class TestUtils {

    private final ObjectMapper mapper;

    public <T> T readJson(String path, Class<T> entityClass) throws IOException, URISyntaxException {
        File file = new File(this.getClass().getClassLoader().getResource("jsonForTests/" + path).toURI());
        return mapper.readValue(file, entityClass);
    }
}
