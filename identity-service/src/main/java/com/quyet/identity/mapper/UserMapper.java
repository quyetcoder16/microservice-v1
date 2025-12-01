package com.quyet.identity.mapper;

import com.quyet.identity.dto.request.user.UserCreateRequest;
import com.quyet.identity.dto.response.user.UserCreateResponse;
import com.quyet.identity.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toEntity(UserCreateRequest userCreateRequest);

  UserCreateResponse toUserCreateResponse(User user);
}
