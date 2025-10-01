package com.quyet.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "permission_id", updatable = false, nullable = false)
  String permissionId;

  @Column(name = "permission_name", unique = true, nullable = false)
  String permissionName;

  @Column(name = "permission_description", columnDefinition = "TEXT")
  String permissionDescription;
}
