package com.quanlychitieu.model;

import java.math.BigDecimal;

/**
 * Dùng để gom dữ liệu thống kê thu/chi theo ngày, phục vụ vẽ biểu đồ Chart.js
 * và hiển thị bằng JSTL/EL trên JSP.
 * LƯU Ý: phải có đủ getter public thì EL (${thongKe.tongThu}) mới đọc được,
 * nếu thiếu getter sẽ gây lỗi HTTP 500 khi JSP render.
 */
public class ThongKe {
    private String ngay;       // dạng yyyy-MM-dd, dùng làm nhãn trục X
    private BigDecimal tongThu;
    private BigDecimal tongChi;

    public ThongKe() {}

    public ThongKe(String ngay, BigDecimal tongThu, BigDecimal tongChi) {
        this.ngay = ngay;
        this.tongThu = tongThu;
        this.tongChi = tongChi;
    }

    public String getNgay() { return ngay; }
    public void setNgay(String ngay) { this.ngay = ngay; }

    public BigDecimal getTongThu() { return tongThu; }
    public void setTongThu(BigDecimal tongThu) { this.tongThu = tongThu; }

    public BigDecimal getTongChi() { return tongChi; }
    public void setTongChi(BigDecimal tongChi) { this.tongChi = tongChi; }
}
