package com.cjd.dwptest.service;

import org.springframework.stereotype.Component;

import com.cjd.dwptest.domain.Point;
import com.cjd.dwptest.domain.Distance;

@Component(value="HaversineCalculator")
public class HaversineCalculator implements DistanceCalculator {

	// approximation of the radius
	// of the average circumference of the earth
	// in kilometers
	private static final double R = 6371;
	

	/*
		This uses the ‘haversine’ formula to calculate the great-circle distance between two points
		– that is, the shortest distance over the earth’s surface 
		– giving an ‘as-the-crow-flies’ distance between the points

		see https://rosettacode.org/wiki/Haversine_formula#Java
	*/
	@Override
	public Distance getDistanceBetweenPoints(final Point p1, final Point p2) {
		if (!p1.isValidLatitude() || !p2.isValidLatitude()) {
			throw new IllegalArgumentException("latitude is outside the range 90 to -90");
		}

		if (!p1.isValidLongitude() || !p2.isValidLongitude()) {
			throw new IllegalArgumentException("longitude is outside the range 180 to -180");
		}

		final double lat1 = p1.getLatitude();
		final double lat2 = p2.getLatitude();
		final double lon1 = p1.getLongitude();
		final double lon2 = p2.getLongitude();
		final double lat1Radians = Math.toRadians(lat1);
		final double lat2Radians = Math.toRadians(lat2);
		final double latDiffRadians = Math.toRadians(lat2 - lat1);
		final double lonDiffRadians = Math.toRadians(lon2 - lon1);

		final double a = Math.pow(Math.sin(latDiffRadians / 2), 2)
				+ Math.pow(Math.sin(lonDiffRadians / 2), 2) * Math.cos(lat1Radians) * Math.cos(lat2Radians);
		final double c = 2 * Math.asin(Math.sqrt(a));

		return Distance.ofKilometers(R * c);
	}

}
