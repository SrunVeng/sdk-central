package com.dcc.sdkcentral.responseBuilder;

import com.dcc.sdkcentral.constant.ApiErrorCode;
import com.dcc.sdkcentral.constant.DevErrorConstant;
import com.dcc.sdkcentral.constant.MessageType;
import com.dcc.sdkcentral.trace.TraceIdUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ResponseMessageBuilder<T> {

    private final TraceIdUtils traceIdUtils;

    @Value("${spring.application.name}")
    private String applicationName;

    public ResponseMessage<@Nullable T> success(@Nullable T data) {
        return ResponseMessage.<@Nullable T>builder()
                .message(DevErrorConstant.SUCCESS)
                .traceId(traceIdUtils.getTraceId())
                .source(applicationName)
                .messageType(MessageType.SUCCESS)
                .errorCode(ApiErrorCode.SUCCESS)
                .devErrorCode(ApiErrorCode.SUCCESS)
                .devMessage(DevErrorConstant.SUCCESS)
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public ResponseMessage<@Nullable Object> failed() {
        return ResponseMessage.<@Nullable Object>builder()
                .message(DevErrorConstant.SUCCESS)
                .messageType(MessageType.SUCCESS)
                .errorCode(ApiErrorCode.SUCCESS)
                .traceId(traceIdUtils.getTraceId())
                .source(applicationName)
                .devErrorCode(ApiErrorCode.SUCCESS)
                .devMessage(DevErrorConstant.SUCCESS)
                .status(HttpStatus.OK)
                .data(null)
                .build();
    }
}
