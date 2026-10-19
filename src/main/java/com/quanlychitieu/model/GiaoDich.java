package com.quanlychitieu.model;

import java.math.BigDecimal;
import java.sql.Date;

public class GiaoDich {
    private int id;
    private int userId;
    private int danhMucId;
    private String tenDanhMuc; // dùng khi JOIN để hiển thị ra JSP
    private String loai; // "THU" hoặc "CHI"
    private BigDecimal soTien;
    private Date ngay;
    private String ghiChu;

    public GiaoDich() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getDanhMucId() { return danhMucId; }
    public void setDanhMucId(int danhMucId) { this.danhMucId = danhMucId; }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }

    public Date getNgay() { return ngay; }
    public void setNgay(Date ngay) { this.ngay = ngay; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
