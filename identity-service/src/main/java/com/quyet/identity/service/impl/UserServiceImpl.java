package com.quyet.identity.service.impl;

import com.quyet.identity.constants.RedisConstant;
import com.quyet.identity.dto.ApiBaseResponse;
import com.quyet.identity.dto.request.user.UserCreateRequest;
import com.quyet.identity.dto.response.user.UserCreateResponse;
import com.quyet.identity.entity.User;
import com.quyet.identity.exception.AppException;
import com.quyet.identity.exception.ErrorCode;
import com.quyet.identity.mapper.UserMapper;
import com.quyet.identity.repository.UserRepository;
import com.quyet.identity.service.RedisService;
import com.quyet.identity.service.UserService;
import com.quyet.identity.utils.OtpUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  RedisService redisService;
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  UserMapper userMapper;

  @Override
  public ResponseEntity<ApiBaseResponse<UserCreateResponse>> createUser(UserCreateRequest request) {

    if (userRepository.existsUserByUserName(request.getUserName())) {
      throw new AppException(ErrorCode.USERNAME_EXISTED);
    }

    if (userRepository.existsUserByEmail(request.getEmail())) {
      throw new AppException(ErrorCode.EMAIL_EXISTED);
    }

    if (userRepository.existsUserByPhoneNumber(request.getPhoneNumber())) {
      throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
    }

    User user = userMapper.toEntity(request);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setIsEnabled(false);
    user.setIsLocked(false);
    user = userRepository.save(user);

    // TODO: add user roles

    // Generate and store OTP code in Redis
    String email = user.getEmail();
    String verificationCode = OtpUtils.generateOtpCode(6);
    redisService.setValueWithTTL(
        String.format(RedisConstant.AUTH_OTP_EMAIL_PREFIX, email),
        verificationCode,
        5,
        TimeUnit.MINUTES); // expire in 5 minutes
    log.info("Verification code for email {}: {}", email, verificationCode);

    redisService.setValueWithTTL(
        String.format(RedisConstant.AUTH_OTP_EMAIL_RETRY_PREFIX, email), 1, 1, TimeUnit.HOURS);

    //    TODO: send verification email

    return ResponseEntity.ok(
        ApiBaseResponse.<UserCreateResponse>builder()
            .result(userMapper.toUserCreateResponse(user))
            .build());
  }
}
