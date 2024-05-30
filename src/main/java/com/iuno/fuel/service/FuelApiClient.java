package com.iuno.fuel.service;

import com.iuno.fuel.dto.FuelResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuelApiClient {

    @Value("${ioBroker.getUrl}")
    private String ioBrokerGetUrl;

    public Map<String, Double> fetchFullPrice() {

        RestTemplate restTemplate = new RestTemplate();

        String urlPriceAvanti = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.0.prices.0.amount";
        String urlPriceJet = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.1.prices.0.amount";
        String urlPriceHofer = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.3.prices.0.amount";
        String urlPriceTurmoel = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.2.prices.0.amount";
        String urlPriceAvia = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.5.prices.0.amount";


        var oResponsePriceAvanti = Optional.ofNullable(restTemplate.getForObject(urlPriceAvanti, FuelResponse.class));
        var oResponsePriceJet = Optional.ofNullable(restTemplate.getForObject(urlPriceJet, FuelResponse.class));
        var oResponsePriceHofer = Optional.ofNullable(restTemplate.getForObject(urlPriceHofer, FuelResponse.class));
        var oResponsePriceTurmoel = Optional.ofNullable(restTemplate.getForObject(urlPriceTurmoel, FuelResponse.class));
        var oResponsePriceAvia = Optional.ofNullable(restTemplate.getForObject(urlPriceAvia, FuelResponse.class));

        Map<String, Double> priceMap = new HashMap<>();

        if (checkFuelPrice(oResponsePriceAvanti)) {
            priceMap.put("Avanti", oResponsePriceAvanti.get().getVal());
        }

        if (checkFuelPrice(oResponsePriceJet)) {
            priceMap.put("Jet", oResponsePriceJet.get().getVal());
        }

        if (checkFuelPrice(oResponsePriceHofer)) {
            priceMap.put("Hofer", oResponsePriceHofer.get().getVal());
        }

        if (checkFuelPrice(oResponsePriceTurmoel)) {
            priceMap.put("Turmöl (Mold)", oResponsePriceTurmoel.get().getVal());
        }

        if (checkFuelPrice(oResponsePriceAvia)) {
            priceMap.put("Avia (Gr. Burgstall", oResponsePriceAvanti.get().getVal());
        }
        return priceMap;
    }

    public String mapToStringFuelPrice(Map<String, Double> priceMap) {
                return priceMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> String.format("%s: %.3f", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private Boolean checkFuelPrice(Optional<FuelResponse> oResponse) {
        if (oResponse.isPresent() && oResponse.get().getVal() != null) {
            return true;
        }
        return false;
    }
}
