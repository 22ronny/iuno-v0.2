package com.iuno;

import com.iuno.fuel.service.FuelService;
import com.iuno.weather.service.WeatherApiClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private final WeatherApiClient weatherApiClient;
    private final FuelService fuelService;


    public ScheduledTask(WeatherApiClient weatherApiClient, FuelService fuelService) {
        this.weatherApiClient = weatherApiClient;
        this.fuelService = fuelService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void checkWasteDate() {
        weatherApiClient.fetchAndSaveTemperature();
    }

//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "0 */30 * * * *")
    public void addFullPriceToDatabase() {
        fuelService.getFuelStationPrice();
    }
}
