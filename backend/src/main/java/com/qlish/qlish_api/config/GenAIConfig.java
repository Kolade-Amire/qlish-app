package com.qlish.qlish_api.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenAIConfig {

    @Value("${gemini-api.key}")
    private String apiKey;

    @Bean
    public Client client(){
        return Client.builder()
                .apiKey(apiKey)
                .build();
    }
}
