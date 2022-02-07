package com.vanguard.interview.weather.provider.openweather.domain.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class CoordinateVO implements Serializable {
    private BigDecimal lon;
    private BigDecimal lat;

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
