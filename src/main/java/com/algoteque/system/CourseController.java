package com.algoteque.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        System.out.println("providers: " + providersTopics.getProviderTopics());

        AtomicInteger counter = new AtomicInteger(1);
        LinkedHashMap<String, Integer[]> teacherRequests = teacherRequest.getTopics().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // Key remains the same (e.g., "Math")
                        entry -> new Integer[]{entry.getValue(), counter.getAndIncrement()}, // Convert value to Map<Integer, Integer>
                        (existing, replacement) -> existing, // No merging needed
                        LinkedHashMap::new // Collect into LinkedHashMap
                ));
        for (Map.Entry<String, Integer[]> entry : teacherRequests.entrySet()) {
            System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
        }
        //System.out.println("teacherRequests: " + teacherRequests); //pick top 3 requests




      /*  Map<String, Integer[]> providerToRequest = new LinkedHashMap<>();
        Set<String> teacherTopics = teacherRequests.keySet();
        for (Map.Entry<String, List<String>> provider : providersTopics.getProviderTopics().entrySet()) {
            for (String providerTopic : provider.getValue()) {
                if (teacherTopics.contains(providerTopic)) {
                    if (providerToRequest.containsKey(provider.getKey())) {
                        Integer[] requestValAndPriority = providerToRequest.get(provider.getKey());
                        requestValAndPriority
                        newItem.put(teacherRequests.get(providerTopic), teacherRequests.get(providerTopic).get(0));
                        providerToRequest.add(teacherRequests.get(providerTopic));
                    } else {
                        providerToRequest.put(provider.getKey(), new ArrayList<>(teacherRequests.get(providerTopic)));
                    }

                }
            }
        }*/


        Map<String, Integer> quotesPerProvider = new LinkedHashMap<>();
        Map<String, List<String>> topicsPerProvider = new LinkedHashMap<>();
        Integer quote = 0;


        return teacherRequest;
    }
}
