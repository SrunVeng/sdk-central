package com.dcc.sdkcentral.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String devCode;
    private final String devMsg;
    private final HttpStatus httpStatus;

    public BusinessException(String devCode, String devMsg) {
        super(devMsg);
        this.devCode = devCode;
        this.devMsg = devMsg;
        this.httpStatus = null;
    }

    public BusinessException(String devCode, String devMsg, HttpStatus httpStatus) {
        super(devMsg);
        this.devCode = devCode;
        this.devMsg = devMsg;
        this.httpStatus = httpStatus;
    }

    public BusinessException(String devCode, String devMsg, Throwable cause) {
        super(devMsg, cause);
        this.devCode = devCode;
        this.devMsg = devMsg;
        this.httpStatus = null;
    }

    public BusinessException(String devCode, String devMsg, Throwable cause, HttpStatus httpStatus) {
        super(devMsg, cause);
        this.devCode = devCode;
        this.devMsg = devMsg;
        this.httpStatus = httpStatus;
    }
}
