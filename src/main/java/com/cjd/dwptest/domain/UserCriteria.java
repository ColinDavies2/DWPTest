package com.cjd.dwptest.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserCriteria {

    private String city;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private DistanceUnit distanceUnit;
    
    UserCriteria() {}

    public UserCriteria(String city, Double latitude, Double longitude,
        Double distance, DistanceUnit distanceUnit) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.distanceUnit = distanceUnit;
    }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

    public DistanceUnit getDistanceUnit() {
		return distanceUnit;
	}

	public void setDistanceUnit(DistanceUnit distanceUnit) {
		this.distanceUnit = distanceUnit;
	}

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
 		builder.append(this.city);
 		builder.append(this.latitude);
		builder.append(this.longitude);
		builder.append(this.distance);
		builder.append(this.distanceUnit);
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
        final UserCriteria other = (UserCriteria) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.city, other.city);
        builder.append(this.latitude, other.latitude);
        builder.append(this.longitude, other.longitude);
        builder.append(this.distance, other.distance);
        builder.append(this.distanceUnit, other.distanceUnit);
        return builder.isEquals();
    }   

}