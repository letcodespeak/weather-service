package com.codespeaks.rest.weather.repository;

import com.codespeaks.rest.weather.constants.Constants;
import com.codespeaks.rest.weather.model.Weather;
import com.codespeaks.rest.weather.model.WeatherId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({Constants.SPRING_PROFILE_TEST})
@DataJpaTest
public class WeatherRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void findByIdWithRecordReturned() {

        Weather weather = new Weather();
        weather.setDescription("Cloudy");
        weather.setCountry("Australia");
        weather.setCity("Sydney");
        entityManager.persist(weather);
        entityManager.flush();

        Optional<Weather> result = weatherRepository.findById(new WeatherId("Australia","Sydney"));
        assertThat(result.isEmpty()).isFalse();
        Weather retrieved = result.get();
        assertThat(retrieved.getCity()).isEqualTo(weather.getCity());
        assertThat(retrieved.getCountry()).isEqualTo(weather.getCountry());
        assertThat(retrieved.getDescription()).isEqualTo(weather.getDescription());
    }

    @Test
    public void findByIdWithNothingReturned() {
        Optional<Weather> result = weatherRepository.findById(new WeatherId("Australia","Perth"));
        assertThat(result.isEmpty()).isTrue();
    }
}
