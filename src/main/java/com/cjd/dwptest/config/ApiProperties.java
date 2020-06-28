package com.cjd.dwptest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("api")
public class ApiProperties {

    private String uri;
    private String defaultCity;
    private double defaultLatitude;
    private double defaultLongitude;
    private double defaultDistance;
    private String defaultDistanceUnit;

    public ApiProperties() {}

    public ApiProperties(String uri, String defaultCity, double defaultLatitude, double defaultLongitude,
        double defaultDistance, String defaultDistanceUnit) {
        this.uri = uri;
        this.defaultCity = defaultCity;
        this.defaultLatitude = defaultLatitude;
        this.defaultLongitude = defaultLongitude;
        this.defaultDistance = defaultDistance;
        this.defaultDistanceUnit = defaultDistanceUnit;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public double getDefaultLatitude() {
        return defaultLatitude;
    }

    public void setDefaultLatitude(double defaultLatitude) {
        this.defaultLatitude = defaultLatitude;
    }

    public double getDefaultLongitude() {
        return defaultLongitude;
    }

    public void setDefaultLongitude(double defaultLongitude) {
        this.defaultLongitude = defaultLongitude;
    }

    public double getDefaultDistance() {
        return defaultDistance;
    }

    public void setDefaultDistance(double defaultDistance) {
        this.defaultDistance = defaultDistance;
    }

    public String getDefaultDistanceUnit() {
        return defaultDistanceUnit;
    }

    public void setDefaultDistanceUnit(String defaultDistanceUnit) {
        this.defaultDistanceUnit = defaultDistanceUnit;
    }

        
}

