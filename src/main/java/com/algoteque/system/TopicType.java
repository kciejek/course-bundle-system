package com.algoteque.system;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TopicType {
    READING("reading"), MATH("math"), SCIENCE("science"), HISTORY("history"), ART("art");

    private final String topicType;

    TopicType(String topicType) {
        this.topicType = topicType;
    }

    @JsonCreator
    public static TopicType fromString(String value) {
        return TopicType.valueOf(value.toUpperCase());
    }
}
