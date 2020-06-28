package com.cjd.dwptest.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import com.cjd.dwptest.domain.User;

import com.cjd.dwptest.config.ApiProperties;
import com.cjd.dwptest.exception.NoEndpointFoundException;

@Service
public class UserClient {
    
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    @Autowired
    public UserClient(final RestTemplateBuilder restTemplateBuilder, final ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
        restTemplate = restTemplateBuilder
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
    }

    public List<User> getAllUsers() {
       final String path = UriComponentsBuilder.fromHttpUrl(apiProperties.getUri()).path("/users").build().toUriString();
        try {
            final ResponseEntity<User[]> response = restTemplate.getForEntity(path, User[].class);
            return Arrays.asList(response.getBody());
            }
        catch (NoEndpointFoundException ex) {
            throw new NoEndpointFoundException("Request path = " + path);
        }
    }

    public List<User> getUsersInCity(final String city) {
        final String path = UriComponentsBuilder.fromHttpUrl(apiProperties.getUri()).path("/city/{city}/users").buildAndExpand(city).toUriString();
        try {
            final ResponseEntity<User[]> response = restTemplate.getForEntity(path, User[].class);
            return Arrays.asList(response.getBody());
        }
        catch (NoEndpointFoundException ex) {
            throw new NoEndpointFoundException("Request path = " + path);
        }
    }
}