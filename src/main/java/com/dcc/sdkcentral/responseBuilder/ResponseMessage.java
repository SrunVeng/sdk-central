package com.dcc.sdkcentral.responseBuilder;

import com.dcc.sdkcentral.constant.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.io.Serializable;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T extends @Nullable Object> implements Serializable {

    @JsonIgnore
    @Serial
    private static final long serialVersionUID = -6627775308255795557L;

    private String traceId;

    private String message;

    private String errorCode;

    private MessageType messageType;

    private String source;

    private String devErrorCode;

    private String devMessage;

    private transient T data;

    private HttpStatus status;
}
