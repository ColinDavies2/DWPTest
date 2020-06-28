package com.cjd.dwptest.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Distance {

    private static final double MILES_TO_KILOMETERS = 1.609344;      
     
    private double kilometers;
      
    private Distance() {}
    private Distance(double kilometers) {
        this.kilometers = kilometers;
    }

    public static Distance ofKilometers(double kilometers) {
        return new Distance(kilometers);
    }

    public static Distance ofMiles(double miles) {
        return new Distance(miles * MILES_TO_KILOMETERS);
    }

    public double getMiles() {
        return kilometers / MILES_TO_KILOMETERS;
    }

    public double getKilometers() {
        return kilometers;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
 		builder.append(this.kilometers);
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
        final Distance other = (Distance) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.kilometers, other.kilometers);
        return builder.isEquals();
    }   
   
}