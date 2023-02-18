package com.service.gpt3integrate.telegrambot;

import com.service.gpt3integrate.service.OpenAIAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

    //Value("${telegram.bot.token}")
    private String BOT_TOKEN="6279101672:AAHdWdPZ2_jWIihiRYwn4biWEWkv99BP0cM";
    //@Value("${telegram.bot.username}")
    private String BOT_USERNAME="@gpt3integrate_bot";

    private static String lastResponse = "";
    private final OpenAIAPIService openAIAPIService;

    public TelegramBot(OpenAIAPIService openAIAPIService){
        this.openAIAPIService = openAIAPIService;

    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageRequest = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            String responseText = openAIAPIService.getOpenAPIResponse(messageRequest);
            lastResponse = responseText;
            SendMessage messageResponse = new SendMessage(chatId,responseText);
            try {
                execute(messageResponse);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    public String getLastResponse() {
        return lastResponse;
    }
}
