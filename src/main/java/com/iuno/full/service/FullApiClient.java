package com.iuno.full.service;

import com.iuno.full.dto.FullResponse;
import com.iuno.weather.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FullApiClient {

    public String fetchFullPrice() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.96:8087/get/";

        String urlPriceAvanti = url + "fuelpricemonitor.1.Horn_Super95.0.prices.0.amount";
        String urlPriceJet = url + "fuelpricemonitor.1.Horn_Super95.1.prices.0.amount";

        FullResponse responsePriceAvanti = restTemplate.getForObject(urlPriceAvanti, FullResponse.class);
        FullResponse responsePriceJet = restTemplate.getForObject(urlPriceJet, FullResponse.class);

        assert responsePriceAvanti != null;
        assert responsePriceJet != null;
        return "Avanti: " + responsePriceAvanti.getVal() + "€ \n" +
                "Jet: " + responsePriceJet.getVal() + "€";
    }

}
