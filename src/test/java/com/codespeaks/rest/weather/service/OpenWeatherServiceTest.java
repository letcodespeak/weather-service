package com.codespeaks.rest.weather.service;

import com.codespeaks.rest.weather.provider.openweather.OpenWeatherApi;
import com.codespeaks.rest.weather.exception.ResourceNotFoundException;
import com.codespeaks.rest.weather.model.Weather;
import com.codespeaks.rest.weather.provider.openweather.domain.response.OpenWeather;
import com.codespeaks.rest.weather.provider.openweather.domain.response.WeatherVO;
import com.codespeaks.rest.weather.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class OpenWeatherServiceTest {

    @InjectMocks
    private OpenWeatherService openWeatherService;

    @Mock
    private OpenWeatherApi openWeatherApi;

    @Mock
    private WeatherRepository weatherRepository;

    @BeforeEach
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void shouldNotMakeAPICallWhenDBHasMatchingRecord(){
        Weather weather = new Weather();
        weather.setCity("Beijing");
        weather.setCountry("China");
        weather.setDescription("rainy");

        Mockito.when(weatherRepository.findById(any())).thenReturn(Optional.of(weather));
        Weather result = openWeatherService.getCurrentWeather("China","Beijing");
        assertThat(result.getCity()).isEqualTo(weather.getCity());
        assertThat(result.getDescription()).isEqualTo(weather.getDescription());
        assertThat(result.getCountry()).isEqualTo(weather.getCountry());
        verify(openWeatherApi, times(0)).getCurrentWeather(any(),any());
    }

    @Test
    public void shouldMakeAPICallWhenDBHasNoMatchingRecord(){
        OpenWeather ow = new OpenWeather();
        WeatherVO wv = new WeatherVO();
        wv.setDescription("rainy");
        wv.setMain("mock");
        wv.setId(99);
        ow.setWeather(Arrays.asList(wv));
        ResponseEntity<OpenWeather> responseEntity = ResponseEntity.ok().body(ow);
        Mockito.when(weatherRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(openWeatherApi.getCurrentWeather(any(),any())).thenReturn(responseEntity);
        Weather result = openWeatherService.getCurrentWeather("China","Beijing");
        assertThat(result.getDescription()).isEqualTo(wv.getDescription());
        verify(openWeatherApi, times(1)).getCurrentWeather(any(),any());
        verify(weatherRepository, times(1)).findById(any());

    }

    @Test
    public void shouldThrowExceptionWhenOpenWeatherAPIReturnsNothing(){
        OpenWeather ow = new OpenWeather();
        WeatherVO wv = new WeatherVO();
        wv.setDescription("rainy");
        wv.setMain("mock");
        wv.setId(99);
        ow.setWeather(Arrays.asList(wv));
        ResponseEntity<OpenWeather> responseEntity = ResponseEntity.ok().build();
        Mockito.when(weatherRepository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(openWeatherApi.getCurrentWeather(any(),any())).thenReturn(responseEntity);
        assertThrows(ResourceNotFoundException.class, () -> openWeatherService.getCurrentWeather("China","Beijing"));
    }

}
