package com.iuno.io_broker.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IoBrokerApiClient {

    public void bulb(int newValue) {

        // Die URL des ioBroker-Endpunkts für den Datenpunkt
        String url = "http://192.168.1.96:8087/set/zigbee.0.00158d00052b3ac2.brightness?value=" + newValue;

        // HTTP-Anfrage-Header konfigurieren
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Die Anfrage-Payload mit dem neuen Wert
        String requestPayload = "{\"val\": " + newValue + "}";

        // Eine RestTemplate-Instanz erstellen
        RestTemplate restTemplate = new RestTemplate();

        // Eine HTTP-Anfrage erstellen und senden, um den Wert des Datenpunkts zu aktualisieren
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(requestPayload, headers), String.class);

        // Die Antwort ausgeben
        System.out.println("HTTP-Statuscode: " + response.getStatusCode());
        System.out.println("Antwortinhalt: " + response.getBody());

    }
}
