-- Tạo bảng permissions
CREATE TABLE permissions (
                             permission_id VARCHAR(36) NOT NULL,
                             permission_name VARCHAR(255) NOT NULL,
                             permission_description TEXT,
                             PRIMARY KEY (permission_id),
                             CONSTRAINT uk_permission_name UNIQUE (permission_name)
);

-- Tạo bảng users
CREATE TABLE users (
                       user_id VARCHAR(36) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255),
                       full_name VARCHAR(255),
                       phone_number VARCHAR(20),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       user_created VARCHAR(36),
                       user_updated VARCHAR(36),
                       PRIMARY KEY (user_id),
                       CONSTRAINT uk_username UNIQUE (username),
                       CONSTRAINT uk_email UNIQUE (email),
                       CONSTRAINT fk_users_user_created FOREIGN KEY (user_created) REFERENCES users(user_id) ON DELETE SET NULL,
                       CONSTRAINT fk_users_user_updated FOREIGN KEY (user_updated) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Tạo bảng roles
CREATE TABLE roles (
                       role_id VARCHAR(36) NOT NULL,
                       role_name VARCHAR(255) NOT NULL,
                       role_description TEXT,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       user_created VARCHAR(36),
                       user_updated VARCHAR(36),
                       PRIMARY KEY (role_id),
                       CONSTRAINT uk_role_name UNIQUE (role_name),
                       CONSTRAINT fk_roles_user_created FOREIGN KEY (user_created) REFERENCES users(user_id) ON DELETE SET NULL,
                       CONSTRAINT fk_roles_user_updated FOREIGN KEY (user_updated) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Tạo bảng user_roles (bảng liên kết giữa users và roles)
CREATE TABLE user_roles (
                            user_id VARCHAR(36) NOT NULL,
                            role_id VARCHAR(36) NOT NULL,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            user_created VARCHAR(36),
                            user_updated VARCHAR(36),
                            PRIMARY KEY (user_id, role_id),
                            CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_user_created FOREIGN KEY (user_created) REFERENCES users(user_id) ON DELETE SET NULL,
                            CONSTRAINT fk_user_roles_user_updated FOREIGN KEY (user_updated) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Tạo bảng role_permissions (bảng liên kết giữa roles và permissions)
CREATE TABLE role_permissions (
                                  role_id VARCHAR(36) NOT NULL,
                                  permission_id VARCHAR(36) NOT NULL,
                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  user_created VARCHAR(36),
                                  user_updated VARCHAR(36),
                                  PRIMARY KEY (role_id, permission_id),
                                  CONSTRAINT fk_role_permissions_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
                                  CONSTRAINT fk_role_permissions_permission_id FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE,
                                  CONSTRAINT fk_role_permissions_user_created FOREIGN KEY (user_created) REFERENCES users(user_id) ON DELETE SET NULL,
                                  CONSTRAINT fk_role_permissions_user_updated FOREIGN KEY (user_updated) REFERENCES users(user_id) ON DELETE SET NULL
);