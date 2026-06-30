package com.dcc.sdkcentral.config;

import com.dcc.sdkcentral.utils.LogMaskingUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogMaskingConfig {

    public LogMaskingConfig(@Value("${mask.keys:}") String keys) {
        LogMaskingUtils.setMaskingKeysFromCsv(keys);
    }
}
