package com.quyet.identity.exception;

import com.quyet.identity.common.ApiBaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  /**
   * handling any exceptions that are not covered by other exception handlers.
   *
   * @param e
   * @param request
   * @return A ResponseEntity containing the error response information.
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ApiBaseResponse> handlingOtherException(
      Exception e, HttpServletRequest request) {
    ApiBaseResponse response = new ApiBaseResponse();
    response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
    response.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getErrorCode());
    return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getHttpStatusCode())
        .body(response);
  }

  // handling app exception
  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<ApiBaseResponse> handlingAppException(
      AppException e, HttpServletRequest request) {
    ErrorCode errorCode = e.getErrorCode();

    ApiBaseResponse response = new ApiBaseResponse();
    response.setMessage(errorCode.getMessage());
    response.setStatusCode(errorCode.getErrorCode());

    return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
  }
}
