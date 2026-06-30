package com.dcc.sdkcentral.config;

import java.net.URI;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

@RequiredArgsConstructor
public class RestClientExecutor {

    private final RestClient restClient;

    public <T> T execute(String httpMethod, String url, Class<T> responseType) {
        return execute(httpMethod, url, null, responseType);
    }

    public <T> T execute(String httpMethod, String url, Object bodyOrParams, Class<T> responseType) {
        return execute(parseHttpMethod(httpMethod), url, bodyOrParams, responseType);
    }

    public <T> T execute(HttpMethod httpMethod, String url, Object bodyOrParams, Class<T> responseType) {
        RestClient.RequestHeadersSpec<?> request = buildRequest(httpMethod, url, bodyOrParams);
        return request.retrieve().body(responseType);
    }

    public <T> T execute(
            String httpMethod, String url, Object bodyOrParams, ParameterizedTypeReference<T> responseType) {
        return execute(parseHttpMethod(httpMethod), url, bodyOrParams, responseType);
    }

    public <T> T execute(
            HttpMethod httpMethod, String url, Object bodyOrParams, ParameterizedTypeReference<T> responseType) {
        RestClient.RequestHeadersSpec<?> request = buildRequest(httpMethod, url, bodyOrParams);
        return request.retrieve().body(responseType);
    }

    private RestClient.RequestHeadersSpec<?> buildRequest(HttpMethod httpMethod, String url, Object bodyOrParams) {
        RestClient.RequestBodySpec request = isQueryParamMethod(httpMethod)
                ? restClient.method(httpMethod).uri(uriBuilder -> buildUri(uriBuilder, url, bodyOrParams))
                : restClient.method(httpMethod).uri(url);

        if (bodyOrParams != null && !isQueryParamMethod(httpMethod)) {
            return request.body(bodyOrParams);
        }

        return request;
    }

    private URI buildUri(UriBuilder uriBuilder, String url, Object params) {
        UriBuilder builder = uriBuilder.path(url);
        addQueryParams(builder, params);
        return builder.build();
    }

    private void addQueryParams(UriBuilder builder, Object params) {
        if (params == null) {
            return;
        }

        if (params instanceof MultiValueMap<?, ?> multiValueMap) {
            multiValueMap.forEach(
                    (name, values) -> values.forEach(value -> builder.queryParam(name.toString(), value)));
            return;
        }

        if (params instanceof Map<?, ?> map) {
            map.forEach((name, value) -> addQueryParam(builder, name, value));
            return;
        }

        throw new IllegalArgumentException("Query params must be a Map or MultiValueMap");
    }

    private void addQueryParam(UriBuilder builder, Object name, Object value) {
        if (value == null) {
            return;
        }

        if (value instanceof Iterable<?> iterable) {
            iterable.forEach(item -> builder.queryParam(name.toString(), item));
            return;
        }

        if (value.getClass().isArray()) {
            builder.queryParam(name.toString(), ObjectUtils.toObjectArray(value));
            return;
        }

        builder.queryParam(name.toString(), value);
    }

    private boolean isQueryParamMethod(HttpMethod httpMethod) {
        return HttpMethod.GET.equals(httpMethod)
                || HttpMethod.DELETE.equals(httpMethod)
                || HttpMethod.HEAD.equals(httpMethod)
                || HttpMethod.OPTIONS.equals(httpMethod);
    }

    private HttpMethod parseHttpMethod(String httpMethod) {
        if (httpMethod == null || httpMethod.isBlank()) {
            throw new IllegalArgumentException("HTTP method is required");
        }
        return HttpMethod.valueOf(httpMethod.trim().toUpperCase(Locale.ROOT));
    }
}
