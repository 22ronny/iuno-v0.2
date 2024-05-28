package com.iuno.weather.service;

import com.iuno.weather.entity.WeatherData;
import com.iuno.weather.repository.WeatherDataRepositoryPostgres;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WeatherService {

    private final WeatherDataRepositoryPostgres repositoryPostgres;


    public WeatherService(WeatherDataRepositoryPostgres repositoryPostgres) {
        this.repositoryPostgres = repositoryPostgres;
    }

    public String getLastWeatherData() {
        WeatherData weatherNow = repositoryPostgres.findFirstByOrderByTimeDesc();
        return weatherNow.getTemperature() +  " °C" + "\n" +
                weatherNow.getPressure() + " hPa Luftdruck" + "\n" +
                weatherNow.getHumidity() + " % Luftfeuchtigkeit";
    }

    public String getHighAndLowTemperatureNight() {
        Instant now = Instant.now();
        Instant yesterday7PM = LocalDateTime.now().minusDays(1).withHour(21).toInstant(ZoneOffset.UTC);
        Instant today7AM = LocalDateTime.now().withHour(6).toInstant(ZoneOffset.UTC);
        List<WeatherData> weatherNight = repositoryPostgres.findByTimeBetween(yesterday7PM, today7AM);
        List<Double> temperatureNight = weatherNight.stream()
                .map(WeatherData::getTemperature)
                .toList();
        Optional<Double> temperaturNightHigh = temperatureNight.stream()
                .max(Double::compareTo);
        Optional<Double> temperatureNightMinimum = temperatureNight.stream()
                .min(Double::compareTo);
        return temperaturNightHigh.map(aDouble -> "Temperatur Nacht \n max: " + aDouble + "\n min: " + temperatureNightMinimum.get()).orElse("Daten fehlen");
    }
}
