package com.vanguard.interview.weather.controller;

import com.vanguard.interview.weather.constants.Constants;
import com.vanguard.interview.weather.exception.ApiKeyNotAllowedException;
import com.vanguard.interview.weather.exception.QuotaExceededException;
import com.vanguard.interview.weather.model.Weather;
import com.vanguard.interview.weather.service.ApiKeyService;
import com.vanguard.interview.weather.service.OpenWeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@WebMvcTest(WeatherController.class)
@ActiveProfiles({Constants.SPRING_PROFILE_TEST})
public class WeatherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OpenWeatherService openWeatherService;

    @MockBean
    ApiKeyService apiKeyService;

    @Test
    public void getCurrentWeather_Success() throws Exception {
        Weather weather = new Weather();
        weather.setCountry("Australia");
        weather.setCity("Melbourne");
        weather.setDescription("Sunny");
        Mockito.when(openWeatherService.getCurrentWeather(anyString(),anyString())).thenReturn(weather);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/weather?city=Melbourne&country=Australia&apikey=key1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is("Sunny")))
                .andExpect(status().isOk());
    }

    @Test
    public void getCurrentWeather_Unauthorized() throws Exception {
        doThrow(new ApiKeyNotAllowedException("supplied api key is not authorized!")).when(apiKeyService).handleApiKey(any());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/weather?city=Melbourne&country=Australia&apikey=xxx")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void getCurrentWeather_BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/weather")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCurrentWeather_Forbidden() throws Exception {
        doThrow(new QuotaExceededException("Maximum access rate hour limit reached, try later!")).when(apiKeyService).handleApiKey(any());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/weather?city=Melbourne&country=Australia&apikey=xxx")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
