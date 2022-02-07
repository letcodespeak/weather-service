package com.codespeaks.rest.weather.provider.openweather.domain.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class WindVO implements Serializable {

    private BigDecimal speed;
    private BigDecimal deg;
    private BigDecimal gust;

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getDeg() {
        return deg;
    }

    public void setDeg(BigDecimal deg) {
        this.deg = deg;
    }

    public BigDecimal getGust() {
        return gust;
    }

    public void setGust(BigDecimal gust) {
        this.gust = gust;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                ", gust=" + gust +
                '}';
    }
}
