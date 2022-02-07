package com.codespeaks.rest.weather.controller;

import com.codespeaks.rest.weather.service.OpenWeatherService;
import com.codespeaks.rest.weather.model.Weather;
import com.codespeaks.rest.weather.service.ApiKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class WeatherController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private OpenWeatherService openWeatherService;
    private ApiKeyService apiKeyService;
    @Autowired
    public WeatherController(OpenWeatherService openWeatherService,ApiKeyService apiKeyService){
        this.openWeatherService = openWeatherService;
        this.apiKeyService = apiKeyService;
    }

    @GetMapping(value="/weather", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Weather> getWeather(@RequestParam Optional<String> country, @RequestParam Optional<String> city, @RequestParam Optional<String> apikey) {
       if (country.isEmpty() || city.isEmpty() || apikey.isEmpty()){
           throw new IllegalArgumentException("city,country, and apikey are required query parameters. Example /restapi/weather?country={country}&city={city}&apikey={apikey}");
       }
        ResponseEntity<Weather> responseEntity = null;
        apiKeyService.handleApiKey(apikey.get());
        Weather weather = openWeatherService.getCurrentWeather(country.get(), city.get());
        responseEntity = new ResponseEntity<>(weather, HttpStatus.OK);

       return responseEntity;
    }

}
