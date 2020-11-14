package ru.guteam.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.guteam.bot.rest.RestClient;
import ru.guteam.bot.sender.PictureSender;

@Service
public class PictureService {
    private PictureSender pictureSender;
    private RestClient restClient;

    @Autowired
    public PictureService(PictureSender pictureSender, RestClient restClient) {
        this.pictureSender = pictureSender;
        this.restClient = restClient;
    }
}
