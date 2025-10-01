package com.quyet.identity.entity;

import com.quyet.identity.utils.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseEntity {
  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  LocalDateTime updatedAt;

  @CreatedBy
  @Column(name = "user_created", updatable = false)
  String userCreated;

  @LastModifiedBy
  @Column(name = "user_updated")
  String userUpdated;

  @PrePersist
  public void prePersist() {
    this.userCreated = SecurityUtils.getCurrentUserId();
    this.userUpdated = this.userCreated;
  }

  @PreUpdate
  public void preUpdate() {
    this.userUpdated = SecurityUtils.getCurrentUserId();
  }
}
