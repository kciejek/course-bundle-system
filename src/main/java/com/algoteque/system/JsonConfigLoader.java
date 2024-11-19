package com.algoteque.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JsonConfigLoader  {

    @Bean
    public ProvidersTopics topics() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("providers-topics.json"),
                ProvidersTopics.class
        );
    }
}

