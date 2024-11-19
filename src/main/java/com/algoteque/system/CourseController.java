package com.algoteque.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Getter
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public List<Quote> sendTeacherRequest(@RequestBody TeacherRequest teacherRequest) {
        final Map<String, Double> quotesPerProvider = courseService.getQuotesPerProvider(teacherRequest);

        return quotesPerProvider.entrySet().stream()
                .map(entry -> new Quote(entry.getKey(), entry.getValue()))
                .toList();
    }
}
