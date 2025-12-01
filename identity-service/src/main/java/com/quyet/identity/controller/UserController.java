package com.quyet.identity.controller;

import com.quyet.identity.dto.ApiBaseResponse;
import com.quyet.identity.constants.UriPath;
import com.quyet.identity.dto.request.user.UserCreateRequest;
import com.quyet.identity.dto.response.user.UserCreateResponse;
import com.quyet.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriPath.V1 + UriPath.USERS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ApiBaseResponse<UserCreateResponse>> createUser(
      @Valid @RequestBody UserCreateRequest userCreateRequest) {
    return userService.createUser(userCreateRequest);
  }
}
