package com.iuno;

import com.iuno.telegramBot.service.TelegramBot;
import com.iuno.weather.service.WeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//@SpringBootApplication
//public class IunoApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(IunoApplication.class, args);
//
//		try {
//			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//			botsApi.registerBot(new TelegramBot());
//		} catch (TelegramApiException e) {
//			e.printStackTrace();
//		}
//	}
//}

@SpringBootApplication
public class IunoApplication {

	public static void main(String[] args) {
		// Starten Sie die Spring-Anwendung und erhalten Sie den ApplicationContext
		ApplicationContext context = SpringApplication.run(IunoApplication.class, args);

		// Erstellen Sie eine Instanz von TelegramBotsApi
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // Holen Sie das TelegramBotService-Bean aus dem ApplicationContext
		WeatherService weatherService = context.getBean(WeatherService.class);

		// Erstellen Sie eine Instanz von TelegramBot und registrieren Sie sie
		try {
			botsApi.registerBot(new TelegramBot(weatherService));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}









