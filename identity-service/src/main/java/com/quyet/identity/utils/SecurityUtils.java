package com.quyet.identity.utils;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    // change when integrate with spring security
    public static String getCurrentUserId() {
        return "id-123";
    }
}
