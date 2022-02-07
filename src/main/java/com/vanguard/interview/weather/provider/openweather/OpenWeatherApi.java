package com.vanguard.interview.weather.provider.openweather;

import com.vanguard.interview.weather.config.properties.WeatherApiProperties;
import com.vanguard.interview.weather.exception.ResourceNotFoundException;
import com.vanguard.interview.weather.provider.openweather.domain.response.DirectGeoCoding;
import com.vanguard.interview.weather.provider.openweather.domain.response.OpenWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class OpenWeatherApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestOperations restOperations;
    private final WeatherApiProperties weatherApiProperties;

    public OpenWeatherApi(RestOperations restOperations, WeatherApiProperties weatherApiProperties) {
        this.restOperations = restOperations;
        this.weatherApiProperties = weatherApiProperties;
    }

    public ResponseEntity<OpenWeather> getCurrentWeather(String country, String city) {
        ResponseEntity<List<DirectGeoCoding>> geoCodingResponseEntity = getDirectGeoCoding(country,city);
        String lat = geoCodingResponseEntity.getBody().get(0).getLat();
        String lon = geoCodingResponseEntity.getBody().get(0).getLon();
        if (Objects.isNull(lat) || Objects.isNull(lon)){
            throw new ResourceNotFoundException("The weather is not available given city= "+ city+ ",country = "+ country);
        }
        String endPoint = weatherApiProperties.getAddress().getBase() + weatherApiProperties.getAddress().getCurrentWeather();
        URI uri = new UriTemplate(endPoint).expand(lat, lon, weatherApiProperties.getApiKey());
        logger.info("Request: " + uri);
        HttpEntity httpEntity = new HttpEntity(getHttpHeaders());
        ResponseEntity<OpenWeather> ow = restOperations.exchange(uri, HttpMethod.GET, httpEntity, OpenWeather.class);
        logger.info("Response: " + ow);
        return ow;
    }

    private ResponseEntity<List<DirectGeoCoding>> getDirectGeoCoding(String country, String city){
        String endPoint = weatherApiProperties.getAddress().getBase() + weatherApiProperties.getAddress().getDirectGeoCoding();
        URI uri = new UriTemplate(endPoint).expand(city, country, weatherApiProperties.getApiKey());
        logger.info("Request: " + uri);
        HttpEntity httpEntity = new HttpEntity(getHttpHeaders());
        ResponseEntity<List<DirectGeoCoding>> dgc = restOperations.exchange(uri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<DirectGeoCoding>>(){});
        if (dgc.getBody().isEmpty()){
            throw new ResourceNotFoundException("The weather is not available given city= "+ city+ ",country = "+ country);
        }
        logger.info("Response: " + dgc);
        return dgc;
    }

    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
