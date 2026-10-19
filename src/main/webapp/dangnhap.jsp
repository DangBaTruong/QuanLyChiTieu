<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="trang-auth">

    <!-- Left decorative panel -->
    <div class="auth-decoration">
        <div class="auth-brand">
            <div class="brand-icon">💰</div>
            <h1>Quản Lý Chi Tiêu</h1>
            <p>Kiểm soát tài chính cá nhân một cách thông minh và hiệu quả.</p>
        </div>
        <div class="auth-features">
            <div class="auth-feature-item">
                <div class="feat-icon">📊</div>
                <span>Thống kê thu chi theo biểu đồ trực quan</span>
            </div>
            <div class="auth-feature-item">
                <div class="feat-icon">🗂️</div>
                <span>Phân loại danh mục linh hoạt, dễ quản lý</span>
            </div>
            <div class="auth-feature-item">
                <div class="feat-icon">⚡</div>
                <span>Thêm giao dịch nhanh, theo dõi tức thì</span>
            </div>
        </div>
    </div>

    <!-- Right form panel -->
    <div class="auth-form-panel">
        <div class="hop-auth">
            <div class="auth-header">
                <h2>Chào mừng trở lại 👋</h2>
                <p>Đăng nhập để tiếp tục quản lý chi tiêu của bạn.</p>
            </div>

            <% if (request.getAttribute("loi") != null) { %>
            <div class="thong-bao-loi">⚠️ ${loi}</div>
            <% } %>
            <% if (request.getAttribute("thongBao") != null) { %>
            <div class="thong-bao-ok">✅ ${thongBao}</div>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/dang-nhap">
                <div class="form-group">
                    <label>Tên đăng nhập</label>
                    <input type="text" name="tenDangNhap" placeholder="Nhập tên đăng nhập..." required autofocus>
                </div>
                <div class="form-group">
                    <label>Mật khẩu</label>
                    <input type="password" name="matKhau" placeholder="Nhập mật khẩu..." required>
                </div>
                <button type="submit" class="btn btn-block" style="margin-top: 8px;">Đăng nhập</button>
            </form>

            <div class="lien-ket-phu">
                Chưa có tài khoản? <a href="${pageContext.request.contextPath}/dang-ky">Đăng ký ngay →</a>
            </div>
        </div>
    </div>

</div>
</body>
</html>
