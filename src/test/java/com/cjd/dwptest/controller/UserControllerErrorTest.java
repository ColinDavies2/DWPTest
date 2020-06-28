package com.cjd.dwptest.controller;

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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import com.cjd.dwptest.domain.UserCriteria;
import com.cjd.dwptest.service.UserService;
import com.cjd.dwptest.config.ApiProperties;
import com.cjd.dwptest.exception.NoEndpointFoundException;


@RunWith(MockitoJUnitRunner.class)
class UserControllerErrorTest {

    @Mock
    private UserService mockUserService;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUsers_withArgumentTypeMismatch() throws Exception {
        final MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(null, null, null, null, null);

         Mockito.when(mockUserService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(any(UserCriteria.class))).thenThrow(exception);

        UserController userController = new UserController(mockUserService, new ApiProperties("path", "city", 1.0, 1.0, 50, "miles")); 
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler())
            .build();
    
        ResultMatcher statusBadRequest = MockMvcResultMatchers.status().isBadRequest();
 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusBadRequest);
    }

    @Test
    void testGetUsers_withIllegalArgument() throws Exception {
        Mockito.when(mockUserService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(any(UserCriteria.class))).thenThrow(new IllegalArgumentException());

        UserController userController = new UserController(mockUserService, new ApiProperties("path", "city", 1.0, 1.0, 50, "miles")); 
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler())
            .build();
    
        ResultMatcher statusBadRequest = MockMvcResultMatchers.status().isBadRequest();
 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusBadRequest);
    }

    @Test
    void testGetUsers_withNoEndpointFound() throws Exception {
        Mockito.when(mockUserService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(any(UserCriteria.class))).thenThrow(new NoEndpointFoundException());

        UserController userController = new UserController(mockUserService, new ApiProperties("path", "city", 1.0, 1.0, 50, "miles")); 
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler())
            .build();
    
        ResultMatcher statusNotFound = MockMvcResultMatchers.status().isNotFound();
 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusNotFound);
    }

    @Test
    void testGetUsers_withAnyOtherRuntimeException() throws Exception {
        Mockito.when(mockUserService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(any(UserCriteria.class))).thenThrow(new RuntimeException());

        UserController userController = new UserController(mockUserService, new ApiProperties("path", "city", 1.0, 1.0, 50, "miles")); 
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler())
            .build();
    
        ResultMatcher statusInternalServerError = MockMvcResultMatchers.status().isInternalServerError();
 
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");
        ResultActions results = mockMvc.perform(builder);

        results.andExpect(statusInternalServerError);
    }


}

