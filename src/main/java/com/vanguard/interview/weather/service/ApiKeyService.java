package com.vanguard.interview.weather.service;

import com.vanguard.interview.weather.config.properties.ApiKeyServiceProperties;
import com.vanguard.interview.weather.exception.ApiKeyNotAllowedException;
import com.vanguard.interview.weather.exception.QuotaExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ApiKeyService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApiKeyServiceProperties apiKeyServiceProperties;
    private Map<String, List<LocalDateTime>> keyStore = new HashMap<>();
    private ReentrantLock lock = new ReentrantLock();

    public void handleApiKey(String apiKey) {
        List<String> keys = apiKeyServiceProperties.getApikeys();
        int ratePerHour = apiKeyServiceProperties.getRatePerHour();
        if (!keys.contains(apiKey)) {
            throw new ApiKeyNotAllowedException("supplied api key is not authorized!");
        }
        try {
            lock.lock();
            List<LocalDateTime> timeTable = keyStore.get(apiKey);
            logger.info("The API key {} has accessed {} times so far.", apiKey, timeTable == null ? 0 : timeTable.size());
            LocalDateTime now = LocalDateTime.now();
            if (Objects.isNull(timeTable)) {
                timeTable = Stream.of(now).collect(Collectors.toList());
                keyStore.put(apiKey, timeTable);
            } else if (timeTable.size() >= ratePerHour) {
                LocalDateTime oneHourAgo = now.minusHours(1);
                List<LocalDateTime> trimmedTimeTable = timeTable.stream().filter(t -> t.isAfter(oneHourAgo)).collect(Collectors.toList());
                if (trimmedTimeTable.size() >= ratePerHour) {
                    throw new QuotaExceededException("Maximum access rate hour limit reached, try later!");
                }
                trimmedTimeTable.add(now);
                keyStore.put(apiKey, trimmedTimeTable);
            } else {
                timeTable.add(now);
            }
        } finally {
            lock.unlock();
        }
    }
}
