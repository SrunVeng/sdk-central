package com.dcc.sdkcentral.trace;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceIdUtils {

    private final Tracer tracer;

    public String getTraceId() {
        Span currentSpan = tracer.currentSpan();
        if (currentSpan == null) {
            return null;
        }

        return currentSpan.context().traceId();
    }

    public String getSpanId() {
        Span currentSpan = tracer.currentSpan();
        if (currentSpan == null) {
            return null;
        }

        return currentSpan.context().spanId();
    }
}
