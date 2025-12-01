package com.quyet.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "role_id", updatable = false, nullable = false)
  String roleId;

  @Column(name = "role_name", nullable = false, unique = true)
  String roleName;

  @Column(name = "role_description", columnDefinition = "TEXT")
  String roleDescription;

  @OneToMany(mappedBy = "role")
  List<UserRole> listUserRole;

  @OneToMany(mappedBy = "role")
  List<RolePermission> listRolePermission;
}
