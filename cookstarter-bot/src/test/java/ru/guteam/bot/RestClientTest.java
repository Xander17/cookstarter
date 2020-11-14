package ru.guteam.bot;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import ru.guteam.bot.rest.RestClient;

public class RestClientTest {
    @Test
    public void restClientTest() {

        RestTemplate restTemplate = new RestTemplate();
        RestClient restClient = new RestClient(restTemplate);

        String token = restClient.getToken("100", "100", "http://localhost:8080/auth");

        System.out.println(token);
    }

}
