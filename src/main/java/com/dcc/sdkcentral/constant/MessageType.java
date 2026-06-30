package com.dcc.sdkcentral.constant;

public enum MessageType {
    ERROR("ERROR"),
    WARN("WARN"),
    BIZ_LOGIC("BIZ_LOGIC"),
    INFO("INFO"),
    SUCCESS("SUCCESS");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
