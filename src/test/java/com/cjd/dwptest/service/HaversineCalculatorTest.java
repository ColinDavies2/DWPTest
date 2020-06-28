package com.cjd.dwptest.service;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cjd.dwptest.domain.Point;
import com.cjd.dwptest.domain.Distance;

// Note: Expected distances (in kilometers) were found using the website:
// https://gps-coordinates.org/distance-between-coordinates.php

class HaversineCalculatorTest
 {
	private static final double PRECISION = 0.001;

	private final HaversineCalculator haversineCalculator = new HaversineCalculator();

	@Test
	void distanceTheNetherlandsNorthToSouth() {
		final Distance expectedDistance = Distance.ofKilometers(304.00102);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(53.478612, 6.250578), new Point(50.752342, 5.916981));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);	
	}
	

	@Test
    void distanceAustraliaWestToEast() {
		final Distance expectedDistance = Distance.ofKilometers(4018.08304);
        final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(-23.939607, 113.585605), new Point(-28.293166, 153.718989));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceFromCapetownToJohannesburg() {
		final Distance expectedDistance = Distance.ofKilometers(1265.06561);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(-33.926510, 18.364603), new Point(-26.208450, 28.040572));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceFromSantiagoToSaoPaolo() {
		final Distance expectedDistance = Distance.ofKilometers(2585.10996);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(-33.464087, -70.660573), new Point(-23.553981, -46.630563));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceIsTheSameIfMeasuredInBothDirections() {
		// testing that distance is the same in whatever direction we measure
		final Distance expectedDistance = Distance.ofKilometers(1265.06561);
		final Distance distanceDirection1 = haversineCalculator.getDistanceBetweenPoints(new Point(-33.926510, 18.364603), new Point(-26.208450, 28.040572));
		final Distance distanceDirection2 = haversineCalculator.getDistanceBetweenPoints(new Point(-26.208450, 28.040572), new Point(-33.926510, 18.364603));
		assertEquals(expectedDistance.getKilometers(), distanceDirection1.getKilometers(), PRECISION);
		assertEquals(expectedDistance.getKilometers(), distanceDirection2.getKilometers(), PRECISION);
	}

	//
	// Corner cases
	//

	@Test
	void distanceAround180thMeridianFiji() {
		// Distance around 180th meridian
		final Distance expectedDistance = Distance.ofKilometers(351.82677);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(-17.947826, 177.221232), new Point(-16.603513, -179.779055));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceFrom180ToMinus180LongitudeShouldBeZero() {
		// Because -180 and 180 longitude is essentially the same, the distance should be zero.
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(20, -180), new Point(20, 180));
		assertEquals(0, distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceAround0thMeridianLondon() {
		final Distance expectedDistance = Distance.ofKilometers(24.67746);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(51.512722, -0.288552), new Point(51.516100, 0.068025));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	// How about poles? Let's look at a couple of distances in the Arctic and Antarctica.
	@Test
	void distanceAround90LatitudeArctic() {
		final Distance expectedDistance = Distance.ofKilometers(0.03881);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(89.9, -179.9), new Point(89.9, 179.9));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void distance90LatitudeNorthPole() {
		// This is the North Pole. When latitude = 90 or -90,
		// longitude doesn't matter - all meridians meet in this point.
		// So with both latitudes = 90, the distance is effectively 0.
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(90, -179.9), new Point(90, 179.9));
		assertEquals(0, distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceAroundMinus90LatitudeAntarctica() {
		// This is the South Pole. When latitude = 90 or -90,
		// Longitude doesn't matter - all meridians meet in this point.
		// So with both latitudes = -90, the distance is effectively 0.
		final Distance expectedDistance = Distance.ofKilometers(0.03881);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(-89.9, -179.9), new Point(-89.9, 179.9));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void distanceFromNorthPoleToSouthPole() {
		// How about from one pole to another? This is max distance on the planet
		final Distance expectedDistance = Distance.ofKilometers(20015.08680);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(90, 10), new Point(-90, 10));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	// Max distance on the planet along the equator
	@Test
	void maxDistanceAlongTheEquator() {
		final Distance expectedDistance = Distance.ofKilometers(20015.08680);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(0, 0), new Point(0, 180));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void maxDistanceAlongTheEquator2() {
		final Distance expectedDistance = Distance.ofKilometers(20015.08680);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(0, 10), new Point(0, -170));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	@Test
	void maxDistanceAlongTheEquator3() {
		final Distance expectedDistance = Distance.ofKilometers(20015.08680);
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(0, 175), new Point(0, -5));
		assertEquals(expectedDistance.getKilometers(), distance.getKilometers(), PRECISION);
	}

	// If both points are absolutely the same, are we getting 0 m distance?
	@Test
	void distanceBetweenTheSamePointsIs0() {
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(42, 99), new Point(42, 99));
		assertEquals(0, distance.getKilometers());
	}

	@Test
	void distanceBetweenTheSamePointsIs0_2() {
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(-42, -99), new Point(-42, -99));
		assertEquals(0, distance.getKilometers());
	}

	@Test
	void distanceBetweenTheSamePoints_0_0_Is0() {
		// Equator meets Greenwich
		final Distance distance = haversineCalculator.getDistanceBetweenPoints(new Point(0, 0), new Point(0, 0));
        assertEquals(0, distance.getKilometers());
    }

    //
    // Negative cases
    //

    @Test
    void invalidLatitude1TooMuch() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(666, 0), new Point(0, 0));
        });
    }

    @Test
    void invalidLatitude1TooLittle() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(-666, 0), new Point(0, 0));
        });
    }

    @Test
    void invalidLatitude2TooMuch() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(0, 0), new Point(666, 0));
        });
    }

    @Test
    void invalidLatitude2TooLittle() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(0, 0), new Point(-666, 0));
        });
    }

    @Test
    void invalidLongitude1TooMuch() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(0, 666), new Point(0, 0));
        });
    }

    @Test
    void invalidLongitude1TooLittle() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(0, -666), new Point(0, 0));
        });
    }

    @Test
    void invalidLongitude2TooMuch() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(0, 0), new Point(0, 666));
        });
    }

    @Test
    void invalidLongitude2TooLittle() {
        assertThrows(IllegalArgumentException.class, () -> {
            haversineCalculator.getDistanceBetweenPoints(new Point(0, 0), new Point(0, -666));
        });
    }
}