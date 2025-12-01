package com.quyet.identity.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RolePermissionId implements Serializable {
  @Column(name = "role_id", updatable = false, nullable = false)
  String roleId;

  @Column(name = "permission_id", updatable = false, nullable = false)
  String permissionId;
}
