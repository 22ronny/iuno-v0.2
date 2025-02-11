package com.iuno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.iuno.telegramBot.service.TelegramBot;

@SpringBootApplication
public class IunoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(IunoApplication.class, args);
		TelegramBot telegramBot = context.getBean(TelegramBot.class);

		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(telegramBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}










