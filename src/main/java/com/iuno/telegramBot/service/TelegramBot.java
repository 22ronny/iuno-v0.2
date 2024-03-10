package com.iuno.telegramBot.service;

import com.iuno.weather.service.WeatherService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final WeatherService service;

    public TelegramBot(WeatherService service) {
        this.service = service;
    }

    private void send(String massage, Update update) {
        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());
        response.setText(massage);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            System.err.println("Fehler beim Senden der Nachricht: " + e.getMessage());
        }
    }

    public void sendMassage(String message) {
        SendMessage response = new SendMessage();
        response.setText(message);
        response.setChatId("878205297"); // Ronald
        try {
            execute(response);
        } catch (TelegramApiException e) {
            System.err.println("Fehler beim Senden der Nachricht: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
//        System.out.println(update.getMessage().getFrom().getFirstName());
//        System.out.println(update.getMessage().getText());

        String command = update.getMessage().getText();
        System.out.println(update.getMessage().getChatId());

        if (command.equals("/weather")) {
            String massage = service.getLastWeatherData();
            send(massage, update);
        }
        if (command.equals("/tempnight")) {
            String massage = service.getHighAndLowTemperatureNight();
            send(massage, update);
        }
        if (command.equals("/commands")) {
            String massage = "/weather \nGibt die Aktuelle Wetterdaten zurück \n/tempnight \nGibt die maximale und minimale Temperatur der letzten Nacht zurück";
            send(massage, update);
        }
    }

    @Override
    public String getBotUsername() {
        return "ronaldhrovat_bot";
    }

    @Override
    public String getBotToken() {
        return "2147034477:AAHbhzY2EMb53_egBk6Qf0sRW356lJmC8f4";
    }
}
