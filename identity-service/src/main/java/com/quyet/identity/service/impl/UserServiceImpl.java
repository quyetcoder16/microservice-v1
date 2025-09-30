package com.quyet.identity.service.impl;

import com.quyet.identity.common.ApiBaseResponse;
import com.quyet.identity.dto.request.user.UserCreateRequest;
import com.quyet.identity.dto.response.user.UserCreateResponse;
import com.quyet.identity.entity.User;
import com.quyet.identity.exception.AppException;
import com.quyet.identity.exception.ErrorCode;
import com.quyet.identity.repository.UserRepository;
import com.quyet.identity.service.RedisService;
import com.quyet.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  RedisService redisService;
  UserRepository userRepository;

  @Override
  public ResponseEntity<ApiBaseResponse<UserCreateResponse>> createUser(UserCreateRequest request) {

    if (userRepository.existsUserByEmail(request.getEmail())) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    redisService.setHash("test", "key1", "value1");
    redisService.setValueWithTTL("key2", "value2", 60, TimeUnit.SECONDS);

    User user =
        userRepository.save(
            User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .build());

    return ResponseEntity.ok(
        ApiBaseResponse.<UserCreateResponse>builder()
            .result(
                UserCreateResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .phoneNumber(user.getPhoneNumber())
                    .build())
            .build());
  }
}
