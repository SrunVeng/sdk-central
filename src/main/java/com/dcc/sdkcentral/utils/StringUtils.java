package com.dcc.sdkcentral.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;

@UtilityClass
public class StringUtils {
    public final String EMPTY = "";

    public boolean hasText(@Nullable final String str) {
        return (str != null && !str.isBlank());
    }

    public boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.isEmpty();
    }

    public boolean isBlank(final CharSequence cs) {
        int strLen = length(cs);
        if (strLen != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public boolean isArray(final Object object) {
        return object != null && object.getClass().isArray();
    }

    public boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof CharSequence charSequence) {
            return charSequence.isEmpty();
        } else if (isArray(object)) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection<?>) object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object instanceof Optional) {
            return ((Optional<?>) object).isEmpty();
        } else {
            return false;
        }
    }

    public String requireNonBlank(final String cs) {
        if (isBlank(cs)) {
            throw new IllegalArgumentException(String.format("%s cannot be blank", cs));
        }

        return cs;
    }
}
