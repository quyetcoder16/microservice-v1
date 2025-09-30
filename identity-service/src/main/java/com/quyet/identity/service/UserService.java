package com.quyet.identity.service;

import com.quyet.identity.common.ApiBaseResponse;
import com.quyet.identity.dto.request.user.UserCreateRequest;
import com.quyet.identity.dto.response.user.UserCreateResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiBaseResponse<UserCreateResponse>> createUser(UserCreateRequest request);
}
