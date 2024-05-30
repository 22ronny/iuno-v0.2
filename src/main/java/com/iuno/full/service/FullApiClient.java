package com.iuno.full.service;

import com.iuno.full.dto.FullResponse;
import com.iuno.weather.dto.WeatherResponse;
import javassist.compiler.ast.Variable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FullApiClient {

    @Value("${ioBroker.getUrl}")
    private String ioBrokerGetUrl;

    public String fetchFullPrice() {

        RestTemplate restTemplate = new RestTemplate();

        String urlPriceAvanti = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.0.prices.0.amount";
        String urlPriceJet = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.1.prices.0.amount";
        String urlPriceHofer = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.3.prices.0.amount";
        String urlPriceTurmoel = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.2.prices.0.amount";
        String urlPriceAvia = ioBrokerGetUrl + "fuelpricemonitor.1.Horn_Super95.5.prices.0.amount";


        var oResponsePriceAvanti = Optional.ofNullable(restTemplate.getForObject(urlPriceAvanti, FullResponse.class));
        var oResponsePriceJet = Optional.ofNullable(restTemplate.getForObject(urlPriceJet, FullResponse.class));
        var oResponsePriceHofer = Optional.ofNullable(restTemplate.getForObject(urlPriceHofer, FullResponse.class));
        var oResponsePriceTurmoel = Optional.ofNullable(restTemplate.getForObject(urlPriceTurmoel, FullResponse.class));
        var oResponsePriceAvia = Optional.ofNullable(restTemplate.getForObject(urlPriceAvia, FullResponse.class));

        Map<String, Double> priceMap = new HashMap<>();

        if (checkFullPrice(oResponsePriceAvanti)) {
            priceMap.put("Avanti", oResponsePriceAvanti.get().getVal());
        }

        if (checkFullPrice(oResponsePriceJet)) {
            priceMap.put("Jet", oResponsePriceJet.get().getVal());
        }

        if (checkFullPrice(oResponsePriceHofer)) {
            priceMap.put("Hofer", oResponsePriceHofer.get().getVal());
        }

        if (checkFullPrice(oResponsePriceTurmoel)) {
            priceMap.put("Turmöl (Mold)", oResponsePriceTurmoel.get().getVal());
        }

        if (checkFullPrice(oResponsePriceAvia)) {
            priceMap.put("Avia (Gr. Burgstall", oResponsePriceAvanti.get().getVal());
        }

        return priceMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> String.format("%s: %.3f", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private Boolean checkFullPrice(Optional<FullResponse> oResponse) {
        if (oResponse.isPresent() && oResponse.get().getVal() != null) {
            return true;
        }
        return false;
    }
}
