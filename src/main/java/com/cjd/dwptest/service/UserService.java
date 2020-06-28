package com.cjd.dwptest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cjd.dwptest.domain.User;
import com.cjd.dwptest.domain.UserCriteria;
import com.cjd.dwptest.domain.Point;
import com.cjd.dwptest.domain.Distance;
import com.cjd.dwptest.domain.DistanceUnit;

@Service
public class UserService {

    private final UserClient userClient;
    private final DistanceCalculator distanceCalculator;

    @Autowired
    public UserService(final UserClient userClient, @Qualifier("HaversineCalculator")final DistanceCalculator distanceCalculator) {
        this.userClient = userClient;
        this.distanceCalculator = distanceCalculator;
    }

    public List<User> findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(final UserCriteria userCriteria) {
        final List<User> cityUsers = userClient.getUsersInCity(userCriteria.getCity());
        final Point point = new Point(userCriteria.getLatitude(), userCriteria.getLongitude());
        final List<User> distanceUsers = findUsersCurrentlyWithinDistanceOfPoint(point, userCriteria.getDistance(), userCriteria.getDistanceUnit());

        return Stream.concat(cityUsers.stream(), distanceUsers.stream()).distinct().collect(Collectors.toList());
    }

    private List<User> findUsersCurrentlyWithinDistanceOfPoint(final Point point, final double distance, final DistanceUnit distanceUnit) {
        final List<User> allUsers = userClient.getAllUsers();
        return filterUsersWithinDistance(allUsers, point, distance, distanceUnit);
    }

    private List<User> filterUsersWithinDistance(final List<User> users, final Point point, final double distance, final DistanceUnit distanceUnit) {
        Distance distanceWithUnit = distanceUnit == DistanceUnit.MILES ? Distance.ofMiles(distance) : Distance.ofKilometers(distance);
        return users.stream()
            .filter(user -> distanceCalculator.getDistanceBetweenPoints(point, new Point(user.getLatitude(), user.getLongitude())).getKilometers() < distanceWithUnit.getKilometers() )
            .collect(Collectors.toList());
    }

}