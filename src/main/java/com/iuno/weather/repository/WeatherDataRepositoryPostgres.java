package com.iuno.weather.repository;

import com.iuno.weather.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface WeatherDataRepositoryPostgres extends JpaRepository<WeatherData, Long> {
    WeatherData findFirstByOrderByTimeDesc();
    List<WeatherData> findByTimeBetween(Instant startTime, Instant endTime);
}
