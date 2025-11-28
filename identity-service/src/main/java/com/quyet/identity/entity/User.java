package com.quyet.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_id", updatable = false, nullable = false)
  String userId;

  @Column(name = "username", unique = true, nullable = false)
  String username;

  @Column(name = "email", unique = true, nullable = false)
  String email;

  @Column(name = "password")
  String password;

  @Column(name = "phone_number")
  String phoneNumber;

  @Builder.Default
  @Column(name = "is_enabled")
  Boolean isEnabled = false;

  @Builder.Default
  @Column(name = "is_locked")
  Boolean isLocked = false;
}
