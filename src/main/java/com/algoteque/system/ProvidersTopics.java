package com.algoteque.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ProvidersTopics {

    @JsonProperty("provider_topics")
    @JsonDeserialize(using = StringToListDeserializer.class)
    private final Map<String, List<String>> providerTopics;


}
