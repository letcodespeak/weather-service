package com.vanguard.interview.weather;

import com.vanguard.interview.weather.constants.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WeatherApplication.class)
@ActiveProfiles({Constants.SPRING_PROFILE_TEST})
class WeatherApplicationTests {

    @Autowired
	private WeatherApplication weatherApplication;

	@Test
	void contextLoads() {
      assertThat(weatherApplication).isNotNull();
	}

}
