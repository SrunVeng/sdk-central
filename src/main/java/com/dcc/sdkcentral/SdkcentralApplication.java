package com.dcc.sdkcentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SdkcentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdkcentralApplication.class, args);
    }
}
