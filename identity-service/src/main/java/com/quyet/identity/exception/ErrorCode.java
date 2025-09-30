package com.quyet.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(1999, "Uncategorized Exception error!", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_EXISTED(1001, "User is existed!", HttpStatus.BAD_REQUEST),
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
