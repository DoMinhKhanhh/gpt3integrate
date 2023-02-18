package com.service.gpt3integrate;

import com.service.gpt3integrate.service.OpenAIAPIService;
import com.service.gpt3integrate.telegrambot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Gpt3integrateApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gpt3integrateApplication.class, args);
	}

	@Bean
	public TelegramBot telegramBot(OpenAIAPIService openAIAPIService){
		TelegramBot telegramBot = new TelegramBot(openAIAPIService);

		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(telegramBot);
		}catch (TelegramApiException ex){
			ex.printStackTrace();
		}
		return telegramBot;

	}

}
