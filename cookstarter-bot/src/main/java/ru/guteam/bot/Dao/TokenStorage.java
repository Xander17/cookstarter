package ru.guteam.bot.Dao;

import org.springframework.web.client.RestTemplate;
import ru.guteam.bot.model.User;
import ru.guteam.bot.rest.RestClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TokenStorage {
    private Map<Integer, String> tokens;


    public void addToken(int userId, String token) {
        tokens.put(userId, token);
    }

    public String getToken(int userId) {
        return tokens.get(userId);
    }

    public void deleteToken(String token) {
        tokens.entrySet().removeIf(entry -> entry.getValue().equals(token));
    }
}
