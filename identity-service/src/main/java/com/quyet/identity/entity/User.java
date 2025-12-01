package com.quyet.identity.entity;

import com.quyet.identity.utils.SecurityUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

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
  String userName;

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

  @OneToMany(mappedBy = "user")
  List<UserRole> listUserRole;

  @Override
  public void prePersist() {
    // 1. Nếu ID chưa có (trường hợp chưa gen), hãy gen ngay lập tức bằng Java
    // Để đảm bảo chúng ta có ID để gán vào userCreated
    if (this.userId == null) {
      this.userId = UUID.randomUUID().toString();
    }

    // 2. Lấy người đang đăng nhập
    String currentUserId = SecurityUtils.getCurrentUserId();

    if (currentUserId != null && !currentUserId.equals("anonymousUser")) {
      // Trường hợp 1: Admin tạo nhân viên -> Lấy ID admin
      this.setUserCreated(currentUserId);
      this.setUserUpdated(currentUserId);
    } else {
      // Trường hợp 2: Đăng ký mới (Chưa ai đăng nhập)
      // -> Lấy chính ID vừa gen ở bước 1 gán vào (Tôi tạo ra tôi)
      this.setUserCreated(this.userId);
      this.setUserUpdated(this.userId);
    }
  }
}
