<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% request.setAttribute("trangHienTai", "thong-ke"); %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê - Quản Lý Chi Tiêu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4"></script>
</head>
<body>
<div class="layout">
    <jsp:include page="/includes/sidebar.jsp" />

    <div class="main-content">
        <div class="page-header">
            <div class="page-header-left">
                <h1>Thống kê</h1>
                <p class="page-subtitle">Thu chi trong 30 ngày gần nhất</p>
            </div>
        </div>

        <!-- Summary cards -->
        <div class="cards">
            <div class="card thu">
                <div class="card-icon">💹</div>
                <div class="nhan">Tổng thu tháng này</div>
                <div class="gia-tri"><fmt:formatNumber value="${tongThuThang}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
            <div class="card chi">
                <div class="card-icon">💸</div>
                <div class="nhan">Tổng chi tháng này</div>
                <div class="gia-tri"><fmt:formatNumber value="${tongChiThang}" groupingUsed="true" maxFractionDigits="0"/> đ</div>
            </div>
        </div>

        <!-- Chart -->
        <div class="panel">
            <h3>📊 Biểu đồ thu / chi theo ngày</h3>
            <c:if test="${empty danhSachThongKe}">
                <div class="empty-state">
                    <div class="empty-icon">📊</div>
                    <p>Chưa có dữ liệu trong 30 ngày gần đây để vẽ biểu đồ.</p>
                </div>
            </c:if>
            <div class="chart-container">
                <canvas id="bieuDoThongKe"></canvas>
            </div>
        </div>
    </div>
</div>

<script>
    const nhanNgay = ${nhanNgayJson};
    const dataThu  = ${dataThuJson};
    const dataChi  = ${dataChiJson};

    const ctx = document.getElementById('bieuDoThongKe');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: nhanNgay,
            datasets: [
                {
                    label: 'Thu',
                    data: dataThu,
                    backgroundColor: 'rgba(16,185,129,.75)',
                    borderColor: '#10b981',
                    borderWidth: 1,
                    borderRadius: 6,
                },
                {
                    label: 'Chi',
                    data: dataChi,
                    backgroundColor: 'rgba(239,68,68,.75)',
                    borderColor: '#ef4444',
                    borderWidth: 1,
                    borderRadius: 6,
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        font: { family: 'Inter, Segoe UI, Arial', size: 13 },
                        padding: 20,
                        usePointStyle: true,
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(ctx) {
                            return ' ' + ctx.dataset.label + ': ' +
                                new Intl.NumberFormat('vi-VN').format(ctx.parsed.y) + ' đ';
                        }
                    }
                }
            },
            scales: {
                x: {
                    grid: { display: false },
                    ticks: { font: { size: 11 } }
                },
                y: {
                    beginAtZero: true,
                    grid: { color: '#f1f5f9' },
                    ticks: {
                        font: { size: 11 },
                        callback: function(value) {
                            if (value >= 1000000) return (value / 1000000).toFixed(1) + 'M';
                            if (value >= 1000)    return (value / 1000).toFixed(0) + 'K';
                            return value;
                        }
                    }
                }
            }
        }
    });
</script>
</body>
</html>
