package com.quyet.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(1999, "Uncategorized Exception error!", HttpStatus.INTERNAL_SERVER_ERROR),
  // general error codes from 1900 to 1999 are reserved for auth exceptions
  TOKEN_CANNOT_CREATED(1998, "Token cannot be created!", HttpStatus.INTERNAL_SERVER_ERROR),

  USER_EXISTED(1901, "User is existed!", HttpStatus.BAD_REQUEST),
  TOKEN_EXPIRED_EXCEPTION(1902, "Token is expired!", HttpStatus.UNAUTHORIZED),
  UNAUTHENTICATED(1903, "Unauthenticated!", HttpStatus.UNAUTHORIZED),

  //    error codes from 1000 to 1100 are reserved for user
  EMAIL_EXISTED(1000, "Email is existed!", HttpStatus.BAD_REQUEST),
  PHONE_NUMBER_EXISTED(1001, "Phone number is existed!", HttpStatus.BAD_REQUEST),
  USERNAME_EXISTED(1002, "Username is existed!", HttpStatus.BAD_REQUEST),
  ;
  private int errorCode;
  private String message;
  private HttpStatus httpStatusCode;

  ErrorCode(int errorCode, String message, HttpStatus httpStatusCode) {
    this.errorCode = errorCode;
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }
}
