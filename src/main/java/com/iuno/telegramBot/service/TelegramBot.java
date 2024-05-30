package com.iuno.telegramBot.service;

import com.iuno.fuel.service.FuelApiClient;
import com.iuno.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final WeatherService WeatherService;
    private final FuelApiClient fuelApiClient;

    public TelegramBot(WeatherService WeatherService, FuelApiClient fuelApiClient) {
        this.WeatherService = WeatherService;
        this.fuelApiClient = fuelApiClient;
    }

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.idAdmin}")
    private String idAdmin;

    private void send(String message, Update update) {
        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());
        response.setText(message);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            System.err.println("Fehler beim Senden der Nachricht: " + e.getMessage());
        }
    }

    public void sendMassage(String message) {
        SendMessage response = new SendMessage();
        response.setText(message);
        response.setChatId(idAdmin); // Ronald
        try {
            execute(response);
        } catch (TelegramApiException e) {
            System.err.println("Fehler beim Senden der Nachricht: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
//        System.out.println(update.getMessage().getFrom().getFirstName()); //Debug-Ausgabe
//        System.out.println(update.getMessage().getText()); //Debug-Ausgabe
//        System.out.println(update.getMessage().getChatId()); //Debug-Ausgabe
//        System.out.println(idAdmin); //Debug-Ausgabe

        String command = update.getMessage().getText();

        if (command.equals("/weather")) {
            String message = WeatherService.getLastWeatherData();
            send(message, update);
        }
        if (command.equals("/tempnight")) {
            String message = WeatherService.getHighAndLowTemperatureNight();
            send(message, update);
        }
        if (command.equals("/commands")) {
            String message = "/weather \n" +
                    "Gibt die Aktuelle Wetterdaten zurück \n" +
                    "/tempnight \n" +
                    "Gibt die maximale und minimale Temperatur der letzten Nacht zurück \n" +
                    "/full \n" +
                    "Spritpreise";
            send(message, update);
        }
        if (command.equals("/full")) {
            String message = fuelApiClient.mapToStringFuelPrice(fuelApiClient.fetchFullPrice());
            send(message, update);
        }
    }

    @Override
    public String getBotUsername() {
//        System.out.println("Bot username: " + username); // Debug-Ausgabe
        return username;
    }

    @Override
    public String getBotToken() {
//        System.out.println("Bot token: " + token); // Debug-Ausgabe
        return token;
    }
}

