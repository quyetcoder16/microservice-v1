package com.quyet.identity.utils;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    // change when integrate with spring security
    public static Long getCurrentUserId() {
        return 1L;
    }
}
