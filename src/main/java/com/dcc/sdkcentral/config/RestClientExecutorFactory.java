package com.dcc.sdkcentral.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestClientExecutorFactory {

    private final RestClientFactory restClientFactory;

    private final Map<String, RestClientExecutor> cache = new ConcurrentHashMap<>();

    public RestClientExecutor getExecutor(String clientName) {
        return cache.computeIfAbsent(clientName, name -> new RestClientExecutor(restClientFactory.getClient(name)));
    }
}
