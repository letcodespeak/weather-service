package com.codespeaks.rest.weather.service;

import com.codespeaks.rest.weather.config.properties.ApiKeyServiceProperties;
import com.codespeaks.rest.weather.exception.ApiKeyNotAllowedException;
import com.codespeaks.rest.weather.exception.QuotaExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class ApiKeyServiceTest {

    @InjectMocks
    private ApiKeyService apiKeyService;
    @Mock
    private ApiKeyServiceProperties apiKeyServiceProperties;

    @BeforeEach
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void shouldThrowExceptionWhenSuppliedAPIKeyIsNotDefined(){
        List<String> keys = Arrays.asList("Key1","Key2");
        Mockito.when(apiKeyServiceProperties.getApikeys()).thenReturn(keys);
        Mockito.when(apiKeyServiceProperties.getRatePerHour()).thenReturn(1);
        assertThrows(ApiKeyNotAllowedException.class, () -> apiKeyService.handleApiKey("KeyNotDefined"));
    }

    @Test
    public void shouldThrowExceptionWhenAccessRateExceedsMaximumAllowed(){
        List<String> keys = Arrays.asList("Key1","Key2");
        Mockito.when(apiKeyServiceProperties.getApikeys()).thenReturn(keys);
        Mockito.when(apiKeyServiceProperties.getRatePerHour()).thenReturn(1);
        apiKeyService.handleApiKey("Key1");
        assertThrows(QuotaExceededException.class, () -> apiKeyService.handleApiKey("Key1"));
    }

}
