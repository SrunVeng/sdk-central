package com.dcc.sdkcentral.config;

import com.dcc.sdkcentral.utils.LogMaskingUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class RequestLoggingFilter implements Filter {

    private static final List<String> EXCLUDED_PATHS =
            List.of("/actuator", "/swagger-ui", "/v3/api-docs", "/favicon.ico");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        // Skip noisy endpoints
        if (shouldSkip(path)) {
            chain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest, 1025);

        ContentCachingResponseWrapper wrappedResponse =
                new ContentCachingResponseWrapper((HttpServletResponse) response);

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
        } finally {

            String requestBody = new String(
                    wrappedRequest.getContentAsByteArray(),
                    Optional.ofNullable(request.getCharacterEncoding()).orElse(StandardCharsets.UTF_8.name()));

            log.info(
                    "Incoming Request: {} {} Body: {}",
                    wrappedRequest.getMethod(),
                    wrappedRequest.getRequestURI(),
                    LogMaskingUtils.mask(requestBody));

            String responseBody = new String(
                    wrappedResponse.getContentAsByteArray(),
                    Optional.ofNullable(response.getCharacterEncoding()).orElse(StandardCharsets.UTF_8.name()));

            log.info(
                    "Outgoing Response: {} {} Body: {}",
                    wrappedRequest.getMethod(),
                    wrappedRequest.getRequestURI(),
                    responseBody);

            wrappedResponse.copyBodyToResponse();
        }
    }

    private boolean shouldSkip(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}
