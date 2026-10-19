<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="trang-auth">

    <!-- Left decorative panel -->
    <div class="auth-decoration">
        <div class="auth-brand">
            <div class="brand-icon">💰</div>
            <h1>Bắt đầu miễn phí</h1>
            <p>Tạo tài khoản và bắt đầu kiểm soát tài chính cá nhân ngay hôm nay.</p>
        </div>
        <div class="auth-features">
            <div class="auth-feature-item">
                <div class="feat-icon">🔒</div>
                <span>Dữ liệu cá nhân được bảo mật an toàn</span>
            </div>
            <div class="auth-feature-item">
                <div class="feat-icon">📱</div>
                <span>Theo dõi mọi lúc, mọi nơi</span>
            </div>
            <div class="auth-feature-item">
                <div class="feat-icon">🎯</div>
                <span>Đặt mục tiêu và theo dõi tiến độ</span>
            </div>
        </div>
    </div>

    <!-- Right form panel -->
    <div class="auth-form-panel">
        <div class="hop-auth">
            <div class="auth-header">
                <h2>Tạo tài khoản mới ✨</h2>
                <p>Điền thông tin bên dưới để bắt đầu sử dụng.</p>
            </div>

            <% if (request.getAttribute("loi") != null) { %>
            <div class="thong-bao-loi">⚠️ ${loi}</div>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/dang-ky">
                <div class="form-group">
                    <label>Họ tên</label>
                    <input type="text" name="hoTen" placeholder="Nguyễn Văn A">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" placeholder="email@example.com">
                </div>
                <div class="form-group">
                    <label>Tên đăng nhập <span style="color:var(--danger)">*</span></label>
                    <input type="text" name="tenDangNhap" placeholder="Tên đăng nhập..." required>
                </div>
                <div class="form-group">
                    <label>Mật khẩu <span style="color:var(--danger)">*</span></label>
                    <input type="password" name="matKhau" placeholder="Mật khẩu..." required>
                </div>
                <div class="form-group">
                    <label>Xác nhận mật khẩu <span style="color:var(--danger)">*</span></label>
                    <input type="password" name="xacNhanMatKhau" placeholder="Nhập lại mật khẩu..." required>
                </div>
                <button type="submit" class="btn btn-block" style="margin-top: 8px;">Tạo tài khoản</button>
            </form>

            <div class="lien-ket-phu">
                Đã có tài khoản? <a href="${pageContext.request.contextPath}/dang-nhap">Đăng nhập →</a>
            </div>
        </div>
    </div>

</div>
</body>
</html>
