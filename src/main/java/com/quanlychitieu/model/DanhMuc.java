package com.quanlychitieu.model;

public class DanhMuc {
    private int id;
    private String tenDanhMuc;
    private String loai; // "THU" hoặc "CHI"
    private int userId;

    public DanhMuc() {}

    public DanhMuc(int id, String tenDanhMuc, String loai, int userId) {
        this.id = id;
        this.tenDanhMuc = tenDanhMuc;
        this.loai = loai;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
