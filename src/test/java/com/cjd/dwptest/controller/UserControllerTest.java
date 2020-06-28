package com.cjd.dwptest.controller;

import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.cjd.dwptest.domain.User;
import com.cjd.dwptest.domain.UserCriteria;
import com.cjd.dwptest.service.UserService;
import com.cjd.dwptest.config.ApiProperties;



@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {

    @Mock
    private UserService mockUserService;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private List<User> testUsers = Arrays.asList(new User[] {new User(1, "first", "last", "email", "ip", 1.0, 2.0)});

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockUserService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(any(UserCriteria.class))).thenReturn(testUsers);

        UserController userController = new UserController(mockUserService, new ApiProperties("path", "city", 1.0, 1.0, 50, "miles")); 
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .build();
    }

    @Test
    void testGetUsers_withNoQueryParameters() throws Exception {
        ResultMatcher statusOk = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedContent = MockMvcResultMatchers.content().json(mapper.writeValueAsString(testUsers));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusOk);
        results.andExpect(expectedContent);
    }

    @Test
    void testGetUsers_withCityQueryParameter() throws Exception {
        ResultMatcher statusOk = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedContent = MockMvcResultMatchers.content().json(mapper.writeValueAsString(testUsers));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users?city=London");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusOk);
        results.andExpect(expectedContent);
    }

    @Test
    void testGetUsers_withLatitudeQueryParameter() throws Exception {
        ResultMatcher statusOk = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedContent = MockMvcResultMatchers.content().json(mapper.writeValueAsString(testUsers));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users?latitude=50.0");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusOk);
        results.andExpect(expectedContent);
    }

    @Test
    void testGetUsers_withLongitudeQueryParameter() throws Exception {
        ResultMatcher statusOk = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedContent = MockMvcResultMatchers.content().json(mapper.writeValueAsString(testUsers));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users?longitude=50.0");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusOk);
        results.andExpect(expectedContent);
    }

    @Test
    void testGetUsers_withDistanceQueryParameter() throws Exception {
        ResultMatcher statusOk = MockMvcResultMatchers.status().isOk();
        ResultMatcher expectedContent = MockMvcResultMatchers.content().json(mapper.writeValueAsString(testUsers));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users?distanceInMiles=50.0");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusOk);
        results.andExpect(expectedContent);
    }
 


}

