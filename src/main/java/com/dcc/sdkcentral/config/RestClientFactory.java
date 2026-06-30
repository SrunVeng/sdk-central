package com.dcc.sdkcentral.config;

import com.dcc.sdkcentral.properties.SdkHttpClientProperties;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class RestClientFactory {

    private final SdkHttpClientProperties properties;

    private final Map<String, RestClient> cache = new ConcurrentHashMap<>();

    public RestClient getClient(String clientName) {
        return cache.computeIfAbsent(clientName, this::buildClient);
    }

    public RestClientExecutor getExecutor(String clientName) {
        return new RestClientExecutor(getClient(clientName));
    }

    private RestClient buildClient(String clientName) {
        SdkHttpClientProperties.ClientConfig clientConfig =
                properties.getClients().get(clientName);

        if (clientConfig == null) {
            throw new IllegalArgumentException("Rest client config not found for client name: " + clientName);
        }

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(properties.resolveConnectTimeout(clientConfig));

        requestFactory.setReadTimeout(properties.resolveReadTimeout(clientConfig));

        RestClient.Builder builder = RestClient.builder().requestFactory(requestFactory);

        if (clientConfig.getBaseUrl() != null && !clientConfig.getBaseUrl().isBlank()) {
            builder.baseUrl(clientConfig.getBaseUrl());
        }

        return builder.build();
    }
}
