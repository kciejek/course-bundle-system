package com.algoteque.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TeacherRequest {
    private final Map<String, String> topics; //TODO change key to TopicType

}
