package com.cjd.dwptest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class User {

    private Integer id;
    
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("ip_address")
    private String ipAddress;

    private double latitude;

    private double longitude;
    
    public User(final Integer id, final String firstName, final String lastName, final String email,
            final String ipAddress, final double latitude, final double longitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.id);
		builder.append(this.firstName);
		builder.append(this.lastName);
		builder.append(this.email);
		builder.append(this.ipAddress);
		builder.append(this.latitude);
		builder.append(this.longitude);
		return builder.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }        
        final User other = (User) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.id, other.id);
        builder.append(this.firstName, other.firstName);
        builder.append(this.lastName, other.lastName);
        builder.append(this.email, other.email);
        builder.append(this.ipAddress, other.ipAddress);
        builder.append(this.latitude, other.latitude);
        builder.append(this.longitude, other.longitude);
        return builder.isEquals();
    }   
}