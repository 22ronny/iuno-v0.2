package com.iuno.full.service;

import com.iuno.full.dto.FullResponse;
import com.iuno.weather.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FullApiClient {

    public String fetchFullPrice() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.96:8087/get/";

        String urlPriceAvanti = url + "fuelpricemonitor.1.Horn_Super95.0.prices.0.amount";
        String urlPriceJet = url + "fuelpricemonitor.1.Horn_Super95.1.prices.0.amount";
        String urlPriceHofer = url + "fuelpricemonitor.1.Horn_Super95.3.prices.0.amount";
        String urlPriceTurmoel = url + "fuelpricemonitor.1.Horn_Super95.2.prices.0.amount";
        String urlPriceAvia = url + "fuelpricemonitor.1.Horn_Super95.5.prices.0.amount";

        var oResponsePriceAvanti = Optional.ofNullable(restTemplate.getForObject(urlPriceAvanti, FullResponse.class));
        var oResponsePriceJet = Optional.ofNullable(restTemplate.getForObject(urlPriceJet, FullResponse.class));
        var oResponsePriceHofer = Optional.ofNullable(restTemplate.getForObject(urlPriceHofer, FullResponse.class));
        var oResponsePriceTurmoel = Optional.ofNullable(restTemplate.getForObject(urlPriceTurmoel, FullResponse.class));
        var oResponsePriceAvia = Optional.ofNullable(restTemplate.getForObject(urlPriceAvia, FullResponse.class));

        Map<String, Double> priceMap = new HashMap<>();

        oResponsePriceAvanti.ifPresent(response -> {
            if (response.getVal() != null && response.getVal() > 0) {
                priceMap.put("Avanti", response.getVal());
            }
        });

        oResponsePriceJet.ifPresent(response -> {
            if (response.getVal() != null && response.getVal() > 0) {
                priceMap.put("Jet", response.getVal());
            }
        });

        oResponsePriceHofer.ifPresent(response -> {
            if (response.getVal() != null && response.getVal() > 0) {
                priceMap.put("Hofer", response.getVal());
            }
        });

        oResponsePriceTurmoel.ifPresent(response -> {
            if (response.getVal() != null && response.getVal() > 0) {
                priceMap.put("Turmöl (Mold)", response.getVal());
            }
        });

        oResponsePriceAvia.ifPresent(response -> {
            if (response.getVal() != null && response.getVal() > 0) {
                priceMap.put("Avia (Gr. Burgstall)", response.getVal());
            }
        });

        return priceMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> String.format("%s: %.3f", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
