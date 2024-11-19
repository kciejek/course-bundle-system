package com.algoteque.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(force = true)
public class Topics {
    private Map<String, String> a;
    private Map<String, String> b;
    private Map<String, String> c;
}
