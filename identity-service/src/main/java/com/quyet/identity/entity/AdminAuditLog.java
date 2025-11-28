package com.quyet.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_audit_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminAuditLog {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  String id;

  @Column(name = "action", nullable = false)
  String action; // LOCK, UNLOCK

  @Column(name = "reason", columnDefinition = "TEXT")
  String reason;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  LocalDateTime createdAt;

  // Lưu ý: Dùng String cho ID để tránh load toàn bộ object User khi ghi log,
  // nhưng nếu cần join thì dùng @ManyToOne. Ở đây tôi dùng String cho nhẹ.
  @Column(name = "admin_id", nullable = false)
  String adminId;

  @Column(name = "user_id", nullable = false)
  String userId;
}
