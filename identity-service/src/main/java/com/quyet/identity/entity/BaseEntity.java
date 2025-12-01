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

  @Column(name = "user_created", updatable = false)
  String userCreated;

  @Column(name = "user_updated")
  String userUpdated;

  @PrePersist
  public void prePersist() {
    String currentUser = SecurityUtils.getCurrentUserId();
    if (isValidId(currentUser)) { // check null/anonymous
      this.userCreated = currentUser;
      this.userUpdated = currentUser;
    }
  }

  @PreUpdate
  public void preUpdate() {
    String currentUserId = SecurityUtils.getCurrentUserId();
    if (isValidId(currentUserId)) {
      this.userUpdated = currentUserId;
    }
  }

  private boolean isValidId(String id) {
    // 1. Phải khác null
    // 2. Phải khác "anonymousUser" (User mặc định của Spring Security khi chưa login)
    return id != null && !id.equals("anonymousUser");
  }
}
