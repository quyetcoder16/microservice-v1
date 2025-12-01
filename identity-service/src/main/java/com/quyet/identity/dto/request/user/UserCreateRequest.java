package com.quyet.identity.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateRequest {

  @NotBlank
  @Size(min = 4, message = "Username must be at least 4 characters long")
  private String userName;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

  @NotBlank private String phoneNumber;
}
