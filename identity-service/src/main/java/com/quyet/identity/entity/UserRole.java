package com.quyet.identity.entity;

import com.quyet.identity.entity.keys.UserRoleId;
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
@IdClass(UserRoleId.class)
public class UserRole extends BaseEntity {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    String userId;

    @Id
    @Column(name = "role_id", updatable = false, nullable = false)
    String roleId;
}
