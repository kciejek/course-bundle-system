package com.algoteque.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        LinkedHashMap<String, Integer> teacherRequests = teacherRequest.getTopics().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        System.out.println("teacherRequests: " + teacherRequests); //pick top 3 requests

        Map<String, Integer> quotesPerProvider = new LinkedHashMap<>();
        Integer quote = 0;
        Set<String> teacherTopics = teacherRequests.keySet();
        for (Map.Entry<String, List<String>> provider : providersTopics.getProviderTopics().entrySet()) {
            for (String providerTopic : provider.getValue()) {
                for (String teacherTopic : teacherTopics) {
                    if (teacherTopic.equals(providerTopic)){
                        Integer teacherTopicValue = teacherRequests.get(teacherTopic);

                    }
                }
            }

            quotesPerProvider.put(provider.getKey(), )
        }


        return teacherRequest;
    }
}
