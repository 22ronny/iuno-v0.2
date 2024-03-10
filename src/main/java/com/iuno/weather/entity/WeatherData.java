package com.iuno.weather.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class WeatherData {

    @Id
    @Column(name = "time")
    private Instant time;

    @NotNull
    @Column(nullable = false)
    private double temperature;

    @NotNull
    @Column(nullable = false)
    private double humidity;

    @NotNull
    @Column(nullable = false)
    private double pressure;
}
