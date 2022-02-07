package com.codespeaks.rest.weather.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "api-key-service")
public class ApiKeyServiceProperties {

    private List<String> apikeys;
    private int ratePerHour;

    public int getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(int ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public List<String> getApikeys() {
        return apikeys;
    }

    public void setApikeys(List<String> apikeys) {
        this.apikeys = apikeys;
    }
}
