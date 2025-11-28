-- 1. Xóa cột full_name khỏi bảng users
ALTER TABLE users
DROP COLUMN full_name;

-- 2. Thay đổi kiểu dữ liệu cột id trong bảng admin_audit_logs
-- Chuyển từ BIGINT (Auto Increment) sang VARCHAR(36)
ALTER TABLE admin_audit_logs
    MODIFY COLUMN id VARCHAR(36) NOT NULL;

-- Lưu ý: Lệnh MODIFY trên trong MySQL sẽ tự động loại bỏ thuộc tính AUTO_INCREMENT