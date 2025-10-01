package com.quyet.identity.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRoleId implements Serializable {
  @Column(name = "user_id", updatable = false, nullable = false)
  String userId;

  @Column(name = "role_id", updatable = false, nullable = false)
  String roleId;
}
