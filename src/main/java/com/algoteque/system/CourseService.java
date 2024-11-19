package com.algoteque.system;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseDAO courseDAO;

    public Map<String, Double> getQuotesPerProvider(TeacherRequest teacherRequest) {
        final LinkedHashMap<String, Integer[]> teacherRequests = getTeacherRequests(teacherRequest);
        final Map<String, List<Integer[]>> providerToRequest = getProviderToRequest(teacherRequests);

        return getQuotesPerProvider(providerToRequest);
    }

    private LinkedHashMap<String, Integer[]> getTeacherRequests(TeacherRequest teacherRequest) {
        AtomicInteger priorityCounter = new AtomicInteger(1);
        return teacherRequest.getTopics().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new Integer[]{entry.getValue(), priorityCounter.getAndIncrement()},
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private Map<String, List<Integer[]>> getProviderToRequest(LinkedHashMap<String, Integer[]> teacherRequests) {
        final Map<String, List<Integer[]>> providerToRequest = new LinkedHashMap<>();
        final Set<String> teacherTopics = teacherRequests.keySet();
        final ProvidersTopics providersTopics = courseDAO.getProvidersTopics();
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

        return providerToRequest;
    }

    private Map<String, Double> getQuotesPerProvider(Map<String, List<Integer[]>> providerToRequest) {
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

        return quotesPerProvider;
    }
}
