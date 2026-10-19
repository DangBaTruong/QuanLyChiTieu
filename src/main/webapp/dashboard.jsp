<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setAttribute("trangHienTai", "dashboard"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tổng quan - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
    <jsp:include page="/includes/sidebar.jsp" />

    <div class="main-content">
        <div class="page-header">
            <div class="page-header-left">
                <h1>Tổng quan</h1>
                <p class="page-subtitle">Tổng hợp thu chi tháng này của bạn</p>
            </div>
            <div class="user-greeting">
                <div class="user-avatar">
                    ${sessionScope.nguoiDung.hoTen != null ? sessionScope.nguoiDung.hoTen.substring(0,1).toUpperCase() : sessionScope.nguoiDung.tenDangNhap.substring(0,1).toUpperCase()}
                </div>
                <span>Xin chào, <b>${sessionScope.nguoiDung.hoTen != null ? sessionScope.nguoiDung.hoTen : sessionScope.nguoiDung.tenDangNhap}</b></span>
            </div>
        </div>

        <!-- Stat cards -->
        <div class="cards">
            <div class="card thu">
                <div class="card-icon">💹</div>
                <div class="nhan">Tổng thu tháng này</div>
                <div class="gia-tri"><fmt:formatNumber value="${tongThu}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
            <div class="card chi">
                <div class="card-icon">💸</div>
                <div class="nhan">Tổng chi tháng này</div>
                <div class="gia-tri"><fmt:formatNumber value="${tongChi}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
            <div class="card so-du">
                <div class="card-icon">💰</div>
                <div class="nhan">Số dư hiện tại</div>
                <div class="gia-tri"><fmt:formatNumber value="${soDu}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
        </div>

        <!-- Recent transactions -->
        <div class="panel">
            <div class="panel-header">
                <h3>Giao dịch gần đây</h3>
                <a href="${pageContext.request.contextPath}/giao-dich" class="btn btn-ghost btn-sm">Xem tất cả →</a>
            </div>
            <div class="table-wrapper">
                <table>
                    <thead>
                    <tr>
                        <th>Ngày</th>
                        <th>Danh mục</th>
                        <th>Loại</th>
                        <th>Số tiền</th>
                        <th>Ghi chú</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="gd" items="${ganDay}">
                        <tr>
                            <td style="color:var(--text-secondary); font-size:13px;">${gd.ngay}</td>
                            <td style="font-weight:500;">${gd.tenDanhMuc}</td>
                            <td><span class="badge ${gd.loai == 'THU' ? 'thu' : 'chi'}">${gd.loai}</span></td>
                            <td style="font-weight:600;"><fmt:formatNumber value="${gd.soTien}" groupingUsed="true" maxFractionDigits="0"/> đ</td>
                            <td style="color:var(--text-secondary);">${gd.ghiChu}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty ganDay}">
                        <tr>
                            <td colspan="5">
                                <div class="empty-state">
                                    <div class="empty-icon">📭</div>
                                    <p>Chưa có giao dịch nào. <a href="${pageContext.request.contextPath}/giao-dich">Thêm giao dịch đầu tiên →</a></p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
