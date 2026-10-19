<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("trangHienTai", "toi"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
    <jsp:include page="/includes/sidebar.jsp" />

    <div class="main-content">
        <div class="page-header">
            <div class="page-header-left">
                <h1>Hồ sơ của tôi</h1>
                <p class="page-subtitle">Xem và cập nhật thông tin cá nhân</p>
            </div>
        </div>

        <!-- Profile banner -->
        <div class="profile-header">
            <div class="profile-avatar">
                ${not empty sessionScope.nguoiDung.hoTen ? sessionScope.nguoiDung.hoTen.substring(0,1).toUpperCase() : sessionScope.nguoiDung.tenDangNhap.substring(0,1).toUpperCase()}
            </div>
            <div class="profile-info">
                <h2>${sessionScope.nguoiDung.hoTen != null ? sessionScope.nguoiDung.hoTen : sessionScope.nguoiDung.tenDangNhap}</h2>
                <p>@${sessionScope.nguoiDung.tenDangNhap}
                    <c:if test="${not empty sessionScope.nguoiDung.email}"> · ${sessionScope.nguoiDung.email}</c:if>
                </p>
            </div>
        </div>

        <!-- Stat cards -->
        <div class="cards">
            <div class="card thu">
                <div class="card-icon">💹</div>
                <div class="nhan">Tổng thu (toàn thời gian)</div>
                <div class="gia-tri"><fmt:formatNumber value="${tongThu}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
            <div class="card chi">
                <div class="card-icon">💸</div>
                <div class="nhan">Tổng chi (toàn thời gian)</div>
                <div class="gia-tri"><fmt:formatNumber value="${tongChi}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
            <div class="card so-du">
                <div class="card-icon">💰</div>
                <div class="nhan">Số dư hiện tại</div>
                <div class="gia-tri"><fmt:formatNumber value="${soDu}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
        </div>

        <!-- Account info -->
        <div class="panel">
            <h3>📋 Thông tin tài khoản</h3>
            <div class="table-wrapper">
                <table class="info-table">
                    <tr>
                        <th>Tên đăng nhập</th>
                        <td>${sessionScope.nguoiDung.tenDangNhap}</td>
                    </tr>
                    <tr>
                        <th>Họ tên</th>
                        <td>${sessionScope.nguoiDung.hoTen}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${sessionScope.nguoiDung.email}</td>
                    </tr>
                    <tr>
                        <th>Số giao dịch</th>
                        <td><strong>${soGiaoDich}</strong> giao dịch đã tạo</td>
                    </tr>
                </table>
            </div>
        </div>

        <!-- Additional info (localStorage) -->
        <div class="panel">
            <h3>✏️ Thông tin bổ sung</h3>
            <div class="form-group-block">
                <label>Giới thiệu bản thân</label>
                <input type="text" id="input-gioi-thieu" placeholder="VD: Sinh viên năm 3 Đại học XYZ...">
            </div>
            <div class="form-group-block">
                <label>Số điện thoại</label>
                <input type="text" id="input-so-dien-thoai" placeholder="0900 000 000">
            </div>
            <div class="form-group-block">
                <label>Địa chỉ</label>
                <input type="text" id="input-dia-chi" placeholder="TP. Hồ Chí Minh...">
            </div>
            <div style="display:flex; align-items:center; gap:12px; margin-top:8px;">
                <button class="btn" onclick="luuThongTinCaNhan()">💾 Lưu thông tin</button>
                <span id="thong-bao-luu" style="display:none;" class="save-feedback">✓ Đã lưu!</span>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', taiThongTinCaNhan);
</script>
</body>
</html>
