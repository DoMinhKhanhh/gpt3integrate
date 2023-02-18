package com.service.gpt3integrate.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public interface OpenAIAPIService{
    String getOpenAPIResponse(String message);
}
@Service
class OpenAIAPIServiceImp implements OpenAIAPIService {

    @Value("${openai.api.key}")
    private String openAIAPIKey;

    @Value("${openai.api.model}")
    private String model;
    private static final Integer maxTokens = 1000;
    private static final Duration timeout = Duration.ofSeconds(60L);

    public String getOpenAPIResponse(String message){

        OpenAiService openAiService = new OpenAiService(openAIAPIKey, timeout);
        try{
            CompletionRequest request = CompletionRequest.builder()
                    .maxTokens(maxTokens)
                    .prompt(message)
                    .model(model)
                    .build();
            CompletionResult result = openAiService.createCompletion(request);

            return result.getChoices().get(0).getText();
            //return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "Sorry, something went wrong!";
        }

    }
}
