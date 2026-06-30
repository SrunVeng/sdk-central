package com.dcc.sdkcentral.properties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "my-sdk.http")
public class SdkHttpClientProperties {

    private Duration defaultConnectTimeout = Duration.ofSeconds(5);
    private Duration defaultReadTimeout = Duration.ofSeconds(30);

    private Map<String, ClientConfig> clients = new HashMap<>();

    @Getter
    @Setter
    public static class ClientConfig {

        private String baseUrl;
        private Duration connectTimeout;
        private Duration readTimeout;
    }

    public Duration resolveConnectTimeout(ClientConfig clientConfig) {
        return clientConfig.getConnectTimeout() != null ? clientConfig.getConnectTimeout() : defaultConnectTimeout;
    }

    public Duration resolveReadTimeout(ClientConfig clientConfig) {
        return clientConfig.getReadTimeout() != null ? clientConfig.getReadTimeout() : defaultReadTimeout;
    }
}
