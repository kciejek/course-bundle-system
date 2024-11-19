package com.algoteque.system.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sendTeacherRequest() throws Exception {
        final String jsonPayload = """
                    {
                      "topics": {
                        "reading": 20,
                        "math": 50,
                        "science": 30,
                        "history": 15,
                        "art": 10
                      }
                    }
                """;

        final String response = """
                    [
                        {
                            "provider": "provider_a",
                            "quote": 8.0
                        },
                        {
                            "provider": "provider_b",
                            "quote": 5.0
                        },
                        {
                            "provider": "provider_c",
                            "quote": 10.0
                        }
                    ]
                """;
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}