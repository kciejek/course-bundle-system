package com.algoteque.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ProvidersTopics {

    @JsonProperty("provider_topics")
    private final Map<String, String> providerTopics;


}
