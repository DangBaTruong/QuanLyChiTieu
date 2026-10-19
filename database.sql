-- Script khởi tạo database cho project Quản lý chi tiêu cá nhân
CREATE DATABASE IF NOT EXISTS quanlychitieu CHARACTER SET utf8mb4;
USE quanlychitieu;

CREATE TABLE nguoi_dung (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_dang_nhap VARCHAR(50) NOT NULL UNIQUE,
    mat_khau VARCHAR(255) NOT NULL,
    ho_ten VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE danh_muc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_danh_muc VARCHAR(100) NOT NULL,
    loai ENUM('THU', 'CHI') NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES nguoi_dung(id) ON DELETE CASCADE
);

CREATE TABLE giao_dich (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    danh_muc_id INT NOT NULL,
    loai ENUM('THU', 'CHI') NOT NULL,
    so_tien DECIMAL(15,2) NOT NULL,
    ngay DATE NOT NULL,
    ghi_chu VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES nguoi_dung(id) ON DELETE CASCADE,
    FOREIGN KEY (danh_muc_id) REFERENCES danh_muc(id) ON DELETE CASCADE
);

-- Tài khoản mẫu để test nhanh (mật khẩu: 123456)
INSERT INTO nguoi_dung (ten_dang_nhap, mat_khau, ho_ten, email)
VALUES ('demo', '123456', 'Nguyễn Văn Demo', 'demo@example.com');

-- Danh mục mặc định cho tài khoản demo (user_id = 1)
INSERT INTO danh_muc (ten_danh_muc, loai, user_id) VALUES
('Lương', 'THU', 1),
('Thưởng', 'THU', 1),
('Ăn uống', 'CHI', 1),
('Di chuyển', 'CHI', 1),
('Mua sắm', 'CHI', 1),
('Hóa đơn / Tiện ích', 'CHI', 1);
