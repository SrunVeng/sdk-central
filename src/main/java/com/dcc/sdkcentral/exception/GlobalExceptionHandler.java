package com.dcc.sdkcentral.exception;

import com.dcc.sdkcentral.responseBuilder.ResponseMessage;
import com.dcc.sdkcentral.responseBuilder.ResponseMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseMessageBuilder<Void> responseMessageBuilder;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseMessage<Void>> handleBusinessException(BusinessException ex) {
        ResponseMessage<Void> failed = responseMessageBuilder.failed();
        failed.setDevErrorCode(ex.getDevCode());
        failed.setDevMessage(ex.getDevMsg());
        if (ex.getHttpStatus() != null) {
            failed.setStatus(ex.getHttpStatus());
        }
        return buildResponse(failed);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage<Void>> handleException(Exception ex) {
        ResponseMessage<Void> failed = responseMessageBuilder.failed();
        failed.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return buildResponse(failed);
    }

    private ResponseEntity<ResponseMessage<Void>> buildResponse(ResponseMessage<Void> failed) {
        HttpStatus status = failed.getStatus();
        return ResponseEntity.status(status).body(failed);
    }
}
