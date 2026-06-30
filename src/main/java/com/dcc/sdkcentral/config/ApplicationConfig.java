package com.dcc.sdkcentral.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEvent() {
        String message = String.format("%s is ready", applicationName);
        log.info(message);
    }
}
