package com.vanguard.interview.weather.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class WeatherId implements Serializable {
    @Column
    private String country;
    @Column
    private String city;

    public WeatherId(){

    }

    public WeatherId(String country,String city){
        this.country = country;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherId weatherId = (WeatherId) o;
        return country.equals(weatherId.country) && city.equals(weatherId.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city);
    }

}
