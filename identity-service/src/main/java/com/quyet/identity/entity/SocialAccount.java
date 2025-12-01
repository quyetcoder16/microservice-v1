package com.quyet.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "social_accounts",
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider_name", "provider_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "social_id", updatable = false, nullable = false)
  String socialId;

  @Column(name = "provider_name", nullable = false)
  String providerName; // GOOGLE, FACEBOOK

  @Column(name = "provider_id", nullable = false)
  String providerId;

  @Column(name = "email")
  String email;

  @Column(name = "name")
  String name;

  @Column(name = "avatar_url", columnDefinition = "TEXT")
  String avatarUrl;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  LocalDateTime updatedAt;

  // Quan hệ Many-to-One với User
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  User user;
}
