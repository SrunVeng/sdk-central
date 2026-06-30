package com.dcc.sdkcentral.config;

import com.dcc.sdkcentral.properties.SdkHttpClientProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@ConditionalOnClass(RestClient.class)
@EnableConfigurationProperties(SdkHttpClientProperties.class)
public class SdkHttpClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestClientFactory sdkRestClientFactory(SdkHttpClientProperties properties) {
        return new RestClientFactory(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestClientExecutorFactory sdkRestClientExecutorFactory(RestClientFactory restClientFactory) {
        return new RestClientExecutorFactory(restClientFactory);
    }
}
