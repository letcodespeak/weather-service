package com.codespeaks.rest.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "Weather")
@Entity
@IdClass(WeatherId.class)
public class Weather implements Serializable {

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column
    private String description;
    @Id
    @Column
    @JsonIgnore
    private String country;
    @Id
    @Column
    @JsonIgnore
    private String city;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
