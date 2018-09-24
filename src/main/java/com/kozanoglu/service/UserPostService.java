package com.kozanoglu.service;

import com.kozanoglu.dto.User;
import com.kozanoglu.dto.UserPostResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserPostService {

    private static final Logger LOG = LoggerFactory.getLogger(UserPostService.class);

    private static final String USER_ENDPOINT = "https://jsonplaceholder.typicode.com/users/";
    private static final String POSTS_ENDPOINT = "https://jsonplaceholder.typicode.com/posts?userId=";

    public UserPostResultDTO get(long userId) {
        try {
            CompletableFuture<User> getUser
                    = CompletableFuture.supplyAsync(() -> getResult(USER_ENDPOINT + userId, User.class));
            CompletableFuture<List> getPosts
                    = CompletableFuture.supplyAsync(() -> getResult(POSTS_ENDPOINT + userId, List.class));

            CompletableFuture<UserPostResultDTO> resultCompletableFuture = getUser.thenCombineAsync(getPosts, UserPostResultDTO::new);

            return resultCompletableFuture.get();

        } catch (Exception e) {
            LOG.error("An exception is caught", e);
            throw new RuntimeException(e);
        }
    }

    private static <T extends Object> T getResult(String apiUrl, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        return restTemplate.getForObject(apiUrl, clazz);
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
