package com.vanguard.interview.weather.repository;

import com.vanguard.interview.weather.model.Weather;
import com.vanguard.interview.weather.model.WeatherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, WeatherId> {
}
