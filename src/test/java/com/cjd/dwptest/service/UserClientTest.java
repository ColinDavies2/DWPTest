package com.cjd.dwptest.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.cjd.dwptest.domain.User;
import com.cjd.dwptest.config.ApiProperties;
import com.cjd.dwptest.exception.NoEndpointFoundException;


@RunWith(SpringRunner.class)
@RestClientTest(UserClient.class)
@EnableConfigurationProperties(value = { ApiProperties.class })
class UserClientTest {

	@Autowired
    private UserClient userClient;

	@Autowired
    private ApiProperties apiProperties;

	@Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;


	@Test                                                                                         
	void getAllUsersTest() throws JsonProcessingException {   
		final List<User> expectedUsers = Arrays.asList(new User[] { new User(1, "first", "last", "email", "ip", 1.0, 2.0) });

		this.mockServer.expect(requestTo(apiProperties.getUri() + "/users"))
				.andRespond(withSuccess(objectMapper.writeValueAsString(expectedUsers), MediaType.APPLICATION_JSON));

		final List<User> users = userClient.getAllUsers();
		mockServer.verify();
		assertEquals(users, expectedUsers);
	}

	@Test
	void getUsersInCityTest() throws JsonProcessingException {
		final List<User> expectedUsers = Arrays
				.asList(new User[] { new User(1, "first", "last", "email", "ip", 1.0, 2.0) });

		this.mockServer.expect(requestTo(apiProperties.getUri() + "/city/cityName/users"))
				.andRespond(withSuccess(objectMapper.writeValueAsString(expectedUsers), MediaType.APPLICATION_JSON));

		final List<User> users = userClient.getUsersInCity("cityName");
		mockServer.verify();
		assertEquals(users, expectedUsers);
	}

	@Test
	void getAllUsers_when404Error_thenThrowNoEndpointFound() {

		this.mockServer.expect(requestTo(apiProperties.getUri() + "/users"))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		assertThrows(NoEndpointFoundException.class, () -> {
			userClient.getAllUsers();
		});
	}

	@Test
	void getUsersInCity_when404Error_thenThrowNoEndpointFound() {

		this.mockServer.expect(requestTo(apiProperties.getUri() + "/city/cityName/users"))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		assertThrows(NoEndpointFoundException.class, () -> {
			userClient.getUsersInCity("cityName");
		});
	}

	@Test
	void getAllUsers_when400Error_thenThrowHttpClientError() {

		this.mockServer.expect(requestTo(apiProperties.getUri() + "/users"))
				.andRespond(withStatus(HttpStatus.BAD_REQUEST));

		assertThrows(HttpClientErrorException.class, () -> {
			userClient.getAllUsers();
		});
	}

	@Test
	void getAllUsers_when500Error_thenThrowHttpServerError() {

		this.mockServer.expect(requestTo(apiProperties.getUri() + "/users"))
				.andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

		assertThrows(HttpServerErrorException.class, () -> {
			userClient.getAllUsers();
		});
	}


}
