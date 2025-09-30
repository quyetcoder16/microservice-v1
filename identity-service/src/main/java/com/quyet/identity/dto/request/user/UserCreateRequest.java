package com.quyet.identity.dto.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
}
