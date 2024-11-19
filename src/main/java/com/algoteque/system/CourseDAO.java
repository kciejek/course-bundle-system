package com.algoteque.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CourseDAO {

    public ProvidersTopics getProvidersTopics()  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    getClass().getClassLoader().getResourceAsStream("providers-topics.json"),
                    ProvidersTopics.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
