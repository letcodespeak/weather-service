package com.vanguard.interview.weather.service;

import com.vanguard.interview.weather.exception.ResourceNotFoundException;
import com.vanguard.interview.weather.model.Weather;
import com.vanguard.interview.weather.model.WeatherId;
import com.vanguard.interview.weather.provider.openweather.OpenWeatherApi;
import com.vanguard.interview.weather.provider.openweather.domain.response.OpenWeather;
import com.vanguard.interview.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class OpenWeatherService {

    @Autowired
    private OpenWeatherApi openWeatherApi;
    @Autowired
    private WeatherRepository weatherRepository;

    private ReentrantLock lock = new ReentrantLock();

    public OpenWeatherService(OpenWeatherApi openWeatherApi, WeatherRepository weatherRepository) {
        this.openWeatherApi = openWeatherApi;
        this.weatherRepository = weatherRepository;
    }

    public Weather getCurrentWeather(String country, String city) {
        try {
            lock.lock();
            return weatherRepository.findById(new WeatherId(country, city)).orElseGet(
                    () -> loadWeatherAndSave(country, city));
        } finally {
            lock.unlock();
        }
    }

    private Weather loadWeatherAndSave(String country, String city) {
        ResponseEntity<OpenWeather> ow = openWeatherApi.getCurrentWeather(country, city);
        if (Objects.isNull(ow.getBody()) || ow.getBody().getWeather().isEmpty()){
            throw new ResourceNotFoundException("The weather is not available given city = "+ city+ ",country = "+ country);
        }
        Weather weather = new Weather();
        weather.setDescription(ow.getBody().getWeather().get(0).getDescription());
        weather.setCity(city);
        weather.setCountry(country);
        weatherRepository.save(weather);
        return weather;
    }

}
