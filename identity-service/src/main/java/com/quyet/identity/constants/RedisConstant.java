package com.quyet.identity.constants;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

public class RedisConstant {
  public static final String AUTH_OTP_EMAIL_PREFIX = "auth:otp:email:{%s}";
  public static final String AUTH_OTP_EMAIL_RETRY_PREFIX = "auth:otp:email_retry:{%s}";
}
