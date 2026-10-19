<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setAttribute("trangHienTai", "giao-dich"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giao dịch - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
    <jsp:include page="/includes/sidebar.jsp" />

    <div class="main-content">
        <div class="page-header">
            <div class="page-header-left">
                <h1>Giao dịch</h1>
                <p class="page-subtitle">Quản lý toàn bộ thu chi của bạn</p>
            </div>
        </div>

        <c:if test="${not empty sessionScope.loi}">
            <div class="thong-bao-loi">⚠️ ${sessionScope.loi}</div>
            <c:remove var="loi" scope="session"/>
        </c:if>

        <!-- Form thêm giao dịch -->
        <div class="panel">
            <h3>➕ Thêm giao dịch mới</h3>
            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/giao-dich">
                <input type="hidden" name="action" value="them">
                <div class="form-group">
                    <label>Loại</label>
                    <select name="loai" class="chon-loai" onchange="locDanhMucTheoLoai(this)">
                        <option value="THU">💹 Thu</option>
                        <option value="CHI">💸 Chi</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="danhMucId" class="chon-danh-muc" required>
                        <c:forEach var="dm" items="${danhSachDanhMuc}">
                            <option value="${dm.id}" data-loai="${dm.loai}">${dm.tenDanhMuc}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Số tiền (đ)</label>
                    <input type="number" name="soTien" step="1000" min="0" placeholder="0" required>
                </div>
                <div class="form-group">
                    <label>Ngày</label>
                    <input type="date" name="ngay" required>
                </div>
                <div class="form-group" style="flex:1; min-width:140px;">
                    <label>Ghi chú</label>
                    <input type="text" name="ghiChu" placeholder="Ghi chú tùy chọn...">
                </div>
                <button type="submit" class="btn">+ Thêm</button>
            </form>
        </div>

        <!-- Bộ lọc -->
        <div class="panel">
            <h3>🔍 Bộ lọc</h3>
            <form class="form-inline" method="get" action="${pageContext.request.contextPath}/giao-dich">
                <div class="form-group">
                    <label>Loại</label>
                    <select name="loai">
                        <option value="ALL" ${loaiLoc == 'ALL' ? 'selected' : ''}>Tất cả</option>
                        <option value="THU" ${loaiLoc == 'THU' ? 'selected' : ''}>💹 Thu</option>
                        <option value="CHI" ${loaiLoc == 'CHI' ? 'selected' : ''}>💸 Chi</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="danhMucId">
                        <option value="0">Tất cả danh mục</option>
                        <c:forEach var="dm" items="${danhSachDanhMuc}">
                            <option value="${dm.id}" ${danhMucIdLoc == dm.id ? 'selected' : ''}>${dm.tenDanhMuc}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-outline">Lọc kết quả</button>
                <a href="${pageContext.request.contextPath}/giao-dich" class="btn btn-ghost">Xoá lọc</a>
            </form>
        </div>

        <!-- Danh sách giao dịch -->
        <div class="panel">
            <h3>📋 Danh sách giao dịch</h3>
            <div class="table-wrapper">
                <table>
                    <thead>
                    <tr>
                        <th>Ngày</th>
                        <th>Danh mục</th>
                        <th>Loại</th>
                        <th>Số tiền</th>
                        <th>Ghi chú</th>
                        <th style="text-align:right;">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="gd" items="${danhSachGiaoDich}">
                        <tr>
                            <td style="color:var(--text-secondary); font-size:13px; white-space:nowrap;">${gd.ngay}</td>
                            <td style="font-weight:500;">${gd.tenDanhMuc}</td>
                            <td><span class="badge ${gd.loai == 'THU' ? 'thu' : 'chi'}">${gd.loai}</span></td>
                            <td style="font-weight:600;"><fmt:formatNumber value="${gd.soTien}" groupingUsed="true" maxFractionDigits="0"/> đ</td>
                            <td style="color:var(--text-secondary);">${gd.ghiChu}</td>
                            <td style="text-align:right; white-space:nowrap;">
                                <a class="action-link" href="javascript:void(0)"
                                   onclick="moModalSua(${gd.id}, ${gd.danhMucId}, '${gd.loai}', '${gd.soTien}', '${gd.ngay}', '${gd.ghiChu}')">✏ Sửa</a>
                                <form method="post" action="${pageContext.request.contextPath}/giao-dich"
                                      style="display:inline" onsubmit="return confirm('Xóa giao dịch này?');">
                                    <input type="hidden" name="action" value="xoa">
                                    <input type="hidden" name="id" value="${gd.id}">
                                    <button type="submit" class="action-link xoa" style="background:none;border:none;font-family:inherit;cursor:pointer;">🗑 Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty danhSachGiaoDich}">
                        <tr>
                            <td colspan="6">
                                <div class="empty-state">
                                    <div class="empty-icon">📭</div>
                                    <p>Không có giao dịch nào phù hợp với bộ lọc.</p>
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

<!-- Modal sửa giao dịch -->
<div class="modal-overlay" id="modal-sua-giao-dich">
    <div class="modal-box">
        <div class="modal-header">
            <h3>✏️ Sửa giao dịch</h3>
            <button class="modal-close" type="button" onclick="dongModalSua()">✕</button>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/giao-dich">
            <input type="hidden" name="action" value="sua">
            <input type="hidden" name="id" id="modal-id">
            <div class="form-group">
                <label>Loại</label>
                <select name="loai" id="modal-loai">
                    <option value="THU">💹 Thu</option>
                    <option value="CHI">💸 Chi</option>
                </select>
            </div>
            <div class="form-group">
                <label>Danh mục</label>
                <select name="danhMucId" id="modal-danhMucId">
                    <c:forEach var="dm" items="${danhSachDanhMuc}">
                        <option value="${dm.id}">${dm.tenDanhMuc}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Số tiền (đ)</label>
                <input type="number" name="soTien" id="modal-soTien" step="1000" min="0">
            </div>
            <div class="form-group">
                <label>Ngày</label>
                <input type="date" name="ngay" id="modal-ngay">
            </div>
            <div class="form-group">
                <label>Ghi chú</label>
                <input type="text" name="ghiChu" id="modal-ghiChu" placeholder="Ghi chú tùy chọn...">
            </div>
            <div class="modal-actions">
                <button type="submit" class="btn">💾 Lưu thay đổi</button>
                <button type="button" class="btn btn-ghost" onclick="dongModalSua()">Hủy</button>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    function locDanhMucTheoLoai(select) {
        var loai = select.value;
        var danhMucSelect = select.closest('form').querySelector('.chon-danh-muc');
        var options = danhMucSelect.querySelectorAll('option');
        var coHienThi = false;
        options.forEach(function (opt) {
            var hienThi = (opt.getAttribute('data-loai') === loai);
            opt.style.display = hienThi ? '' : 'none';
            if (hienThi && !coHienThi) { opt.selected = true; coHienThi = true; }
        });
    }
    document.addEventListener('DOMContentLoaded', function () {
        var chonLoai = document.querySelector('.chon-loai');
        if (chonLoai) locDanhMucTheoLoai(chonLoai);
    });
</script>
</body>
</html>
