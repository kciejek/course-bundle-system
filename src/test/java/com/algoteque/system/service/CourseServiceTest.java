package com.algoteque.system.service;

import com.algoteque.system.dao.CourseDAO;
import com.algoteque.system.request.TeacherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceTest {

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        CourseDAO courseDAO = new CourseDAO();
        courseService = new CourseService(courseDAO);
    }

    @Test
    void getQuotesPerProvider() {
        final Map<String, Integer> topics = Map.of("reading", 20, "math", 50, "science",
                30, "history", 15, "art", 10);
        TeacherRequest teacherRequest = new TeacherRequest(topics);
        Map<String, Double> quotesPerProvider = courseService.getQuotesPerProvider(teacherRequest);

        assertTrue(quotesPerProvider.containsKey("provider_a"));
        assertTrue(quotesPerProvider.containsKey("provider_b"));
        assertTrue(quotesPerProvider.containsKey("provider_c"));
        assertEquals(8.0, quotesPerProvider.get("provider_a"));
        assertEquals(5.0, quotesPerProvider.get("provider_b"));
        assertEquals(10.0, quotesPerProvider.get("provider_c"));
    }
}