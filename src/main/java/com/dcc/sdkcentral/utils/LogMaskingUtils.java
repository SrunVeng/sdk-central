package com.dcc.sdkcentral.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogMaskingUtils {

    private final Set<String> maskingKeys = new HashSet<>();

    public void setMaskingKeysFromCsv(String csvKeys) {
        maskingKeys.clear();
        if (csvKeys != null && !csvKeys.isBlank()) {
            for (String key : csvKeys.split(",")) {
                maskingKeys.add(key.trim().toLowerCase());
            }
        }
    }

    /**
     * Returns a masked view of the input for logging.
     * - If input is String, returns masked String.
     * - If input is POJO, returns masked string representation.
     * - If null or no keys set, returns input as string.
     */
    public String mask(Object input) {
        if (input == null) return "null";

        if (maskingKeys.isEmpty()) {
            return input.toString();
        }

        if (input instanceof String) {
            return maskString((String) input);
        } else {
            return maskPojoForView(input);
        }
    }

    private String maskString(String message) {
        if (message == null) return null;

        for (String key : maskingKeys) {
            // key=value or key = value
            String regexEquals = "(?i)(\\b" + Pattern.quote(key) + "\\s*=\\s*)([^,\\s]+)";
            message = message.replaceAll(regexEquals, "$1*****");

            // key: value or key : value
            String regexColon = "(?i)(\\b" + Pattern.quote(key) + "\\s*:\\s*)([^,\\s]+)";
            message = message.replaceAll(regexColon, "$1*****");

            // <key>value</key>
            String regexXml = "(?i)(<" + Pattern.quote(key) + ">)([^<]+)(</" + Pattern.quote(key) + ">)";
            message = message.replaceAll(regexXml, "$1*****$3");

            // "key": "value"
            String regexJson = "(?i)(\"" + Pattern.quote(key) + "\"\\s*:\\s*\")([^\"]+)(\")";
            message = message.replaceAll(regexJson, "$1*****$3");
        }
        return message;
    }

    private String maskPojoForView(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sb = new StringBuilder(clazz.getSimpleName()).append(" { ");

        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                value = "N/A";
            }

            if (maskingKeys.contains(fieldName.toLowerCase()) && value instanceof String) {
                sb.append(fieldName).append("=*****");
            } else {
                sb.append(fieldName).append("=").append(value);
            }
            sb.append(", ");
        });

        if (sb.length() > 2) sb.setLength(sb.length() - 2); // remove trailing comma and space
        sb.append(" }");

        return sb.toString();
    }
}
