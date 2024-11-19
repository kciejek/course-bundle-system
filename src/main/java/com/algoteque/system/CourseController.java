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


        Map<String, List<Integer[]>> providerToRequest = new LinkedHashMap<>();
        Set<String> teacherTopics = teacherRequests.keySet();
        for (Map.Entry<String, List<String>> provider : providersTopics.getProviderTopics().entrySet()) {
            for (String providerTopic : provider.getValue()) {
                if (teacherTopics.contains(providerTopic)) {
                    if (providerToRequest.containsKey(provider.getKey())) {
                        List<Integer[]> requestValAndPriority = providerToRequest.get(provider.getKey());
                        requestValAndPriority.add(teacherRequests.get(providerTopic));
                    } else {
                        providerToRequest.put(provider.getKey(),
                                new ArrayList<>(Arrays.asList(new Integer[][]{teacherRequests.get(providerTopic)})));
                    }
                }
            }
        }
        System.out.println("providerToRequest");
        for (Map.Entry<String, List<Integer[]>> entry : providerToRequest.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (Integer[] requestVal : entry.getValue()) {
                System.out.print(Arrays.toString(requestVal));
            }
            System.out.println();
        }

        Map<String, Double> quotesPerProvider = new LinkedHashMap<>();
        double quote = 0L;
        for (Map.Entry<String, List<Integer[]>> entry : providerToRequest.entrySet()) {
            if (entry.getValue().size() == 2) {
                double topicOneLvl = entry.getValue().get(0)[0];
                double topicTwoLvl = entry.getValue().get(1)[0];
                quote = 0.1 * (topicOneLvl + topicTwoLvl);
                quotesPerProvider.put(entry.getKey(), quote);
            }
            if (entry.getValue().size() == 1) {
                double topicLvlVal = entry.getValue().get(0)[0];
                int topicLvlPrior = entry.getValue().get(0)[1];
                if (topicLvlPrior == 1) {
                    quote = 0.2 * topicLvlVal;
                }
                if (topicLvlPrior == 2) {
                    quote = 0.25 * topicLvlVal;
                }
                if (topicLvlPrior == 3) {
                    quote = 0.3 * topicLvlVal;
                }
                quotesPerProvider.put(entry.getKey(), quote);
            }
        }

        System.out.println("quotesPerProvider");
        for (Map.Entry<String, Double> entry : quotesPerProvider.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }





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


        return teacherRequest;
    }
}
