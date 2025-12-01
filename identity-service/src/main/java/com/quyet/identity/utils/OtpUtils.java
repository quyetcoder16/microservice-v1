package com.quyet.identity.utils;

public class OtpUtils {
    public static String generateOtpCode(int length) {
        StringBuilder otpCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = (int) (Math.random() * 10);
            otpCode.append(digit);
        }
        return otpCode.toString();
    }
}
