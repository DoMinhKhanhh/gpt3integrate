package com.service.gpt3integrate.Test;

import com.service.gpt3integrate.telegrambot.TelegramBot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootTest
public class TelegramBotTest {

    @Autowired
    private TelegramBot telegramBot;

    @Test
    public void testBot() {
        String message = "Hello";
        String expectedResponse = "Hi there! How can I help you?";

        String chatId = "-1001662661895";
        SendMessage sendMessage = new SendMessage(chatId,message);


        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // Wait for response from bot
        try {
            Thread.sleep(10000); // Wait 3 seconds for bot to respond
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if bot responded with expected message
        String actualResponse = telegramBot.getLastResponse();
        Assert.assertEquals(expectedResponse,actualResponse);

    }
}



