package com.codespeaks.rest.weather.config;

import com.codespeaks.rest.weather.config.properties.WeatherApiProperties;
import com.codespeaks.rest.weather.provider.openweather.OpenWeatherApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(WeatherApiProperties.class)
public class OpenWeatherApiConfiguration {

    @Autowired
    private WeatherApiProperties weatherApiProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public OpenWeatherApi createOpenWeatherApi(){
        return new OpenWeatherApi(restTemplate,weatherApiProperties);
    }
}
