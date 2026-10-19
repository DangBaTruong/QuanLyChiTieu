<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String trangHienTai = (String) request.getAttribute("trangHienTai");
    if (trangHienTai == null) trangHienTai = "";
%>
<div class="sidebar">
    <div class="logo">
        <div class="logo-icon">💰</div>
        <div>
            <div class="logo-text">Chi Tiêu</div>
            <div class="logo-sub">Quản lý thông minh</div>
        </div>
    </div>

    <nav>
        <div class="nav-section-label">Menu chính</div>
        <a href="${pageContext.request.contextPath}/dashboard"
           class="<%= "dashboard".equals(trangHienTai) ? "active" : "" %>">
            <span class="nav-icon">🏠</span> Tổng quan
        </a>
        <a href="${pageContext.request.contextPath}/giao-dich"
           class="<%= "giao-dich".equals(trangHienTai) ? "active" : "" %>">
            <span class="nav-icon">💸</span> Giao dịch
        </a>
        <a href="${pageContext.request.contextPath}/danh-muc"
           class="<%= "danh-muc".equals(trangHienTai) ? "active" : "" %>">
            <span class="nav-icon">🗂️</span> Danh mục
        </a>
        <a href="${pageContext.request.contextPath}/thong-ke"
           class="<%= "thong-ke".equals(trangHienTai) ? "active" : "" %>">
            <span class="nav-icon">📊</span> Thống kê
        </a>

        <div class="nav-section-label">Tài khoản</div>
        <a href="${pageContext.request.contextPath}/toi"
           class="<%= "toi".equals(trangHienTai) ? "active" : "" %>">
            <span class="nav-icon">👤</span> Hồ sơ của tôi
        </a>
    </nav>

    <div class="sidebar-footer">
        <a href="${pageContext.request.contextPath}/dang-xuat">
            <span class="nav-icon">🚪</span> Đăng xuất
        </a>
    </div>
</div>
