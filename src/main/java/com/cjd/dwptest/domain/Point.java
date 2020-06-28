package com.cjd.dwptest.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Point {

    private double latitude;
    private double longitude;
     
    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isValidLatitude() {
		return latitude <= 90 && latitude >= -90;
	}

	public boolean isValidLongitude() {
        return longitude <= 180 && longitude >= -180;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
 		builder.append(this.latitude);
		builder.append(this.longitude);
		return builder.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }        
        final Point other = (Point) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.latitude, other.latitude);
        builder.append(this.longitude, other.longitude);
        return builder.isEquals();
    }   
   
}