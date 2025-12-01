-- 1. Cập nhật bảng users: Thêm trạng thái hoạt động và khóa
ALTER TABLE users
    ADD COLUMN is_enabled BOOLEAN DEFAULT FALSE,
    ADD COLUMN is_locked BOOLEAN DEFAULT FALSE;

-- 2. Cập nhật bảng permissions: Thêm các trường Audit (cho đồng bộ với Users)
ALTER TABLE permissions
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN user_created VARCHAR(36),
    ADD COLUMN user_updated VARCHAR(36);

-- Thêm khóa ngoại cho các trường audit của permissions
ALTER TABLE permissions
    ADD CONSTRAINT fk_permissions_user_created FOREIGN KEY (user_created) REFERENCES users(user_id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_permissions_user_updated FOREIGN KEY (user_updated) REFERENCES users(user_id) ON DELETE SET NULL;

-- 3. Tạo bảng social_accounts
CREATE TABLE social_accounts (
                                 social_id VARCHAR(36) NOT NULL,
                                 user_id VARCHAR(36) NOT NULL,
                                 provider_name VARCHAR(50) NOT NULL,
                                 provider_id VARCHAR(255) NOT NULL,
                                 email VARCHAR(255),
                                 name VARCHAR(255),
                                 avatar_url TEXT,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                 PRIMARY KEY (social_id),
    -- Ràng buộc User phải tồn tại
                                 CONSTRAINT fk_social_accounts_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    -- Ràng buộc duy nhất cho cặp Provider + ProviderID
                                 CONSTRAINT uk_social_provider UNIQUE (provider_name, provider_id)
);

-- 4. Tạo bảng admin_audit_logs
-- Lưu ý: admin_id và user_id phải là VARCHAR(36) để khớp với bảng users
CREATE TABLE admin_audit_logs (
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  admin_id VARCHAR(36) NOT NULL,
                                  user_id VARCHAR(36) NOT NULL,
                                  action VARCHAR(50) NOT NULL,
                                  reason TEXT,
                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  PRIMARY KEY (id),
                                  CONSTRAINT fk_audit_logs_admin_id FOREIGN KEY (admin_id) REFERENCES users(user_id) ON DELETE CASCADE,
                                  CONSTRAINT fk_audit_logs_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);