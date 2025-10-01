package com.quyet.identity.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtils {

  public static boolean isValidEmail(String email) {
    String emailRegex = "[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}";
    return email.matches(emailRegex);
  }

}
