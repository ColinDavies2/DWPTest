package com.cjd.dwptest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjd.dwptest.service.UserService;
import com.cjd.dwptest.domain.DistanceUnit;
import com.cjd.dwptest.domain.User;
import com.cjd.dwptest.domain.UserCriteria;
import com.cjd.dwptest.config.ApiProperties;

@RestController
public class UserController {
    
    private final UserService userService;
    private final ApiProperties apiProperties;

    @Autowired
    public UserController(final UserService userService, final ApiProperties apiProperties) {
        this.userService = userService;
        this.apiProperties = apiProperties;
    }

    @GetMapping("/users")
    public List<User> findUsers(@RequestParam(name = "city", required = false) final String city,
                                @RequestParam(name = "latitude", required = false) final Double latitude,
                                @RequestParam(name = "longitude", required = false) final Double longitude,
                                @RequestParam(name = "distance", required = false) final Double distance,
                                @RequestParam(name = "distanceUnit", required = false) final DistanceUnit distanceUnit) {
    
        UserCriteria userCriteria = new UserCriteria(city, latitude, longitude, distance, distanceUnit);
        addDefaultValuesForMissingQueryParameters(userCriteria);
        return userService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(userCriteria);
    }

    private void addDefaultValuesForMissingQueryParameters(UserCriteria userCriteria) {

        if (userCriteria.getCity() == null) {
            userCriteria.setCity(apiProperties.getDefaultCity());
        }
        if (userCriteria.getLatitude() == null) {
            userCriteria.setLatitude(apiProperties.getDefaultLatitude());
        }
        if (userCriteria.getLongitude() == null) {
            userCriteria.setLongitude(apiProperties.getDefaultLongitude());
        }
        if (userCriteria.getDistance() == null) {
            userCriteria.setDistance(apiProperties.getDefaultDistance());
        }
        if (userCriteria.getDistanceUnit() == null) {
            userCriteria.setDistanceUnit(DistanceUnit.valueOf(apiProperties.getDefaultDistanceUnit().toUpperCase()));
        }
    }


}