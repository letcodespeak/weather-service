package com.codespeaks.rest.weather.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "open-weather-api")
public class WeatherApiProperties {
    private Address address;
    private String apiKey;


    public Address getAddress(){
        return this.address;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public String getApiKey(){
        return this.apiKey;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public static class Address{
        private String base;
        private String currentWeather;
        private String directGeoCoding;

        public void setBase(String base){
            this.base = base;
        }

        public String getBase(){
            return this.base;
        }

        public String getCurrentWeather(){
            return this.currentWeather;
        }

        public void setCurrentWeather(String currentWeather){
            this.currentWeather = currentWeather;
        }

        public String getDirectGeoCoding(){
            return this.directGeoCoding;
        }
        public void setDirectGeoCoding(String directGeoCoding){
            this.directGeoCoding = directGeoCoding;
        }
    }
}
