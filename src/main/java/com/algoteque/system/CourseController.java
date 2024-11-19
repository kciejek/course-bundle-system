package com.algoteque.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CourseController {

    //private final ProvidersTopics providersTopics;

    @PostMapping
    public TeacherRequest sendTeacherRequest(@RequestBody TeacherRequest teacherRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProvidersTopics providersTopics = objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("providers-topics.json"),
                ProvidersTopics.class
        );

        System.out.println("providersTopics.getProviderTopics(): " + providersTopics.getProviderTopics());
        return teacherRequest;
    }
}
