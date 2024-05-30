package com.iuno.weather.service;

import com.iuno.weather.dto.WeatherResponse;
import com.iuno.weather.entity.WeatherData;
import com.iuno.weather.repository.WeatherDataRepositoryPostgres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class WeatherApiClient {

    @Value("${ioBroker.getUrl}")
    private String ioBrokerGetUrl;

    private final WeatherDataRepositoryPostgres repositoryPostgres;

    public WeatherApiClient(WeatherDataRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    public void fetchAndSaveTemperature() {
        RestTemplate restTemplate = new RestTemplate();
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemUTC());
        WeatherData weatherData = new WeatherData();

        String urlTemperature = ioBrokerGetUrl + "zigbee.0.00158d0002b0d58d.temperature";
        String urlHumidity = ioBrokerGetUrl + "zigbee.0.00158d0002b0d58d.humidity";
        String urlPressure = ioBrokerGetUrl + "zigbee.0.00158d0002b0d58d.pressure";

        WeatherResponse responseTemperature = restTemplate.getForObject(urlTemperature, WeatherResponse.class);
        WeatherResponse responseHumidity = restTemplate.getForObject(urlHumidity, WeatherResponse.class);
        WeatherResponse responsePressure = restTemplate.getForObject(urlPressure, WeatherResponse.class);

        if (responseTemperature != null && responseTemperature.getVal() != null
                && responseHumidity != null && responseHumidity.getVal() != null
                && responsePressure != null && responsePressure.getVal() != null) {
            weatherData.setTemperature(responseTemperature.getVal());
            weatherData.setHumidity(responseHumidity.getVal());
            weatherData.setPressure(responsePressure.getVal());
            weatherData.setTime(localDateTime.toInstant(ZoneOffset.UTC));
            repositoryPostgres.save(weatherData);
        }
    }
}