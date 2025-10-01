package com.quyet.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermission extends BaseEntity {
  @Id
  @Column(name = "role_id", updatable = false, nullable = false)
  String roleId;

  @Id
  @Column(name = "permission_id", updatable = false, nullable = false)
  String permissionId;
}
