package com.vanguard.interview.weather.provider.openweather.domain.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OpenWeather implements Serializable {

    private List<WeatherVO> weather = new ArrayList<>();
    private WindVO wind;
    private CoordinateVO coord;

    public List<WeatherVO> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherVO> weather) {
        this.weather = weather;
    }

    public WindVO getWind() {
        return wind;
    }

    public void setWind(WindVO wind) {
        this.wind = wind;
    }

    public CoordinateVO getCoord() {
        return coord;
    }

    public void setCoord(CoordinateVO coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "OpenWeather{" +
                "weather=" + weather +
                ", wind=" + wind +
                ", coord=" + coord +
                '}';
    }
}
