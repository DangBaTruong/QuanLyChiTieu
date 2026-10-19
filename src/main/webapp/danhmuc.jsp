<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("trangHienTai", "danh-muc"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh mục - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
    <jsp:include page="/includes/sidebar.jsp" />

    <div class="main-content">
        <div class="page-header">
            <div class="page-header-left">
                <h1>Danh mục</h1>
                <p class="page-subtitle">Quản lý các danh mục thu và chi của bạn</p>
            </div>
        </div>

        <!-- Form thêm danh mục -->
        <div class="panel">
            <h3>➕ Thêm danh mục mới</h3>
            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/danh-muc">
                <input type="hidden" name="action" value="them">
                <div class="form-group" style="flex:1; min-width:200px;">
                    <label>Tên danh mục</label>
                    <input type="text" name="tenDanhMuc" placeholder="VD: Ăn uống, Học phí, Lương..." required>
                </div>
                <div class="form-group">
                    <label>Loại</label>
                    <select name="loai">
                        <option value="THU">💹 Thu</option>
                        <option value="CHI">💸 Chi</option>
                    </select>
                </div>
                <button type="submit" class="btn">+ Thêm</button>
            </form>
        </div>

        <!-- Danh sách danh mục -->
        <div class="panel">
            <h3>🗂️ Danh sách danh mục</h3>
            <div class="table-wrapper">
                <table>
                    <thead>
                    <tr>
                        <th>Tên danh mục</th>
                        <th>Loại</th>
                        <th style="text-align:right;">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dm" items="${danhSachDanhMuc}">
                        <tr>
                            <td style="font-weight:500;">${dm.tenDanhMuc}</td>
                            <td><span class="badge ${dm.loai == 'THU' ? 'thu' : 'chi'}">${dm.loai}</span></td>
                            <td style="text-align:right;">
                                <form method="post" action="${pageContext.request.contextPath}/danh-muc"
                                      style="display:inline"
                                      onsubmit="return confirm('Xóa danh mục này? Các giao dịch thuộc danh mục cũng sẽ bị xóa.');">
                                    <input type="hidden" name="action" value="xoa">
                                    <input type="hidden" name="id" value="${dm.id}">
                                    <button type="submit" class="action-link xoa" style="background:none;border:none;font-family:inherit;cursor:pointer;">🗑 Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty danhSachDanhMuc}">
                        <tr>
                            <td colspan="3">
                                <div class="empty-state">
                                    <div class="empty-icon">🗂️</div>
                                    <p>Chưa có danh mục nào. Hãy thêm danh mục đầu tiên!</p>
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
