package com.codespeaks.rest.weather.repository;

import com.codespeaks.rest.weather.model.Weather;
import com.codespeaks.rest.weather.model.WeatherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, WeatherId> {
}
