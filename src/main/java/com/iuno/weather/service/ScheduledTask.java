package com.iuno.weather.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private final WeatherApiClient weatherApiClient;
    private final WeatherService weatherService;

    public ScheduledTask(WeatherApiClient weatherApiClient, WeatherService weatherService) {
        this.weatherApiClient = weatherApiClient;
        this.weatherService = weatherService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void checkWasteDate() {
        weatherApiClient.fetchAndSaveTemperature();
    }
}
