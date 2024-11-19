package com.algoteque.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Getter
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public List<Quote> sendTeacherRequest(@RequestBody TeacherRequest teacherRequest) throws IOException {
        final Map<String, Double> quotesPerProvider = courseService.getQuotesPerProvider(teacherRequest);
        List<Quote> quotes = new ArrayList<>();
        for (Map.Entry<String, Double> entry : quotesPerProvider.entrySet()) {
            quotes.add(new Quote(entry.getKey(), entry.getValue()));
        }

        return quotes;
    }
}
