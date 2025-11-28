package com.quyet.identity.entity;

import com.quyet.identity.entity.keys.RolePermissionId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(RolePermissionId.class)
public class RolePermission extends BaseEntity {
  @Id
  @Column(name = "role_id", updatable = false, nullable = false)
  String roleId;

  @Id
  @Column(name = "permission_id", updatable = false, nullable = false)
  String permissionId;
}
