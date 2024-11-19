package com.algoteque.system.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Quote {
    private final String provider;
    private final Double quote;
}
