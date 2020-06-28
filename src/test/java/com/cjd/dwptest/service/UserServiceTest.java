package com.cjd.dwptest.service;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import com.cjd.dwptest.domain.User;
import com.cjd.dwptest.domain.UserCriteria;
import com.cjd.dwptest.domain.Point;
import com.cjd.dwptest.domain.DistanceUnit;
import com.cjd.dwptest.domain.Distance;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
	
	@Mock
	private UserClient mockUserClient;

	@Mock
	private HaversineCalculator mockHaversineCalculator;

	private UserService userService;
	
	private List<User> cityUsers;
	private List<User> allUsers;
	
	@BeforeEach
	public void setup() {
        MockitoAnnotations.initMocks(this);

		cityUsers = new ArrayList<User>();
		cityUsers.add(new User(1, "", "", "", "", 51.0, -5.0));
		cityUsers.add(new User(2, "", "", "", "", 52.0, -6.0));
		cityUsers.add(new User(3, "", "", "", "", 53.0, -7.0));
		
		allUsers = new ArrayList<User>();
		allUsers.add(new User(1, "", "", "", "", 51.0, -5.0));
		allUsers.add(new User(2, "", "", "", "", 52.0, -6.0));
		allUsers.add(new User(3, "", "", "", "", 53.0, -7.0));
		allUsers.add(new User(4, "", "", "", "", 50.0, -5.0));
		allUsers.add(new User(5, "", "", "", "", 50.0, -5.0));
		allUsers.add(new User(6, "", "", "", "", 50.0, -5.0));
		
		Mockito.when(mockUserClient.getAllUsers()).thenReturn(allUsers);
		Mockito.when(mockUserClient.getUsersInCity("TESTCITY")).thenReturn(cityUsers);

		userService = new UserService(mockUserClient, mockHaversineCalculator);
	}
	
	@Test
	void findUsersLivingInCityOrCurrentlyWithinDistanceOfPointTest_allInDistance() {
		Mockito.when(mockHaversineCalculator.getDistanceBetweenPoints(any(Point.class), any(Point.class))).thenReturn(Distance.ofMiles(49.0));

		UserCriteria userCriteria = new UserCriteria("TESTCITY", 1.0, 2.0, 50.0, DistanceUnit.MILES);
		final List<User> users = userService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(userCriteria);

		assertEquals(users.size(), allUsers.size());
	}

	@Test
	void findUsersLivingInCityOrCurrentlyWithinDistanceOfPointTest_noneInDistance() {
		Mockito.when(mockHaversineCalculator.getDistanceBetweenPoints(any(Point.class), any(Point.class))).thenReturn(Distance.ofMiles(50.0));

		UserCriteria userCriteria = new UserCriteria("TESTCITY", 1.0, 2.0, 50.0, DistanceUnit.MILES);
		final List<User> users = userService.findUsersLivingInCityOrCurrentlyWithinDistanceOfPoint(userCriteria);

		assertEquals(users.size(), cityUsers.size());
	}

}
