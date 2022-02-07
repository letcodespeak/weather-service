package com.codespeaks.rest.weather.provider.openweather.domain.response;

import java.io.Serializable;

public class DirectGeoCoding  implements Serializable {
    private String name;
    private String lat;
    private String lon;
    private String country;
    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DirectGeoCoding{" +
                "name='" + name + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
