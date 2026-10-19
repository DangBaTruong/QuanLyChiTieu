package com.quanlychitieu.dao;

import com.quanlychitieu.model.GiaoDich;
import com.quanlychitieu.model.ThongKe;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoDichDAO {

    /**
     * Lấy danh sách giao dịch của 1 user, có thể lọc theo loại (THU/CHI) và danh mục.
     * loai = null hoặc "ALL" => không lọc theo loại
     * danhMucId = 0 => không lọc theo danh mục
     */
    public List<GiaoDich> layDanhSach(int userId, String loai, int danhMucId) {
        List<GiaoDich> ds = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT g.*, d.ten_danh_muc FROM giao_dich g " +
            "JOIN danh_muc d ON g.danh_muc_id = d.id " +
            "WHERE g.user_id = ?"
        );
        if (loai != null && !loai.isEmpty() && !loai.equalsIgnoreCase("ALL")) {
            sql.append(" AND g.loai = ?");
        }
        if (danhMucId > 0) {
            sql.append(" AND g.danh_muc_id = ?");
        }
        sql.append(" ORDER BY g.ngay DESC, g.id DESC");

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setInt(idx++, userId);
            if (loai != null && !loai.isEmpty() && !loai.equalsIgnoreCase("ALL")) {
                ps.setString(idx++, loai);
            }
            if (danhMucId > 0) {
                ps.setInt(idx++, danhMucId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ds;
    }

    public GiaoDich layTheoId(int id, int userId) {
        String sql = "SELECT g.*, d.ten_danh_muc FROM giao_dich g " +
                "JOIN danh_muc d ON g.danh_muc_id = d.id " +
                "WHERE g.id = ? AND g.user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean them(GiaoDich gd) {
        String sql = "INSERT INTO giao_dich (user_id, danh_muc_id, loai, so_tien, ngay, ghi_chu) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gd.getUserId());
            ps.setInt(2, gd.getDanhMucId());
            ps.setString(3, gd.getLoai());
            ps.setBigDecimal(4, gd.getSoTien());
            ps.setDate(5, gd.getNgay());
            ps.setString(6, gd.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Cập nhật (sửa) một giao dịch đã có - dùng cho tính năng edit qua modal. */
    public boolean capNhatGiaoDich(GiaoDich gd) {
        String sql = "UPDATE giao_dich SET danh_muc_id = ?, loai = ?, so_tien = ?, ngay = ?, ghi_chu = ? " +
                "WHERE id = ? AND user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gd.getDanhMucId());
            ps.setString(2, gd.getLoai());
            ps.setBigDecimal(3, gd.getSoTien());
            ps.setDate(4, gd.getNgay());
            ps.setString(5, gd.getGhiChu());
            ps.setInt(6, gd.getId());
            ps.setInt(7, gd.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean xoa(int id, int userId) {
        String sql = "DELETE FROM giao_dich WHERE id = ? AND user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Tổng thu và tổng chi trong tháng hiện tại - dùng cho Dashboard. */
    public BigDecimal[] tongThuChiThangHienTai(int userId) {
        String sql = "SELECT " +
                "COALESCE(SUM(CASE WHEN loai = 'THU' THEN so_tien ELSE 0 END), 0) AS tong_thu, " +
                "COALESCE(SUM(CASE WHEN loai = 'CHI' THEN so_tien ELSE 0 END), 0) AS tong_chi " +
                "FROM giao_dich WHERE user_id = ? " +
                "AND MONTH(ngay) = MONTH(CURDATE()) AND YEAR(ngay) = YEAR(CURDATE())";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BigDecimal[]{ rs.getBigDecimal("tong_thu"), rs.getBigDecimal("tong_chi") };
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new BigDecimal[]{ BigDecimal.ZERO, BigDecimal.ZERO };
    }

    /** Tổng thu / tổng chi toàn thời gian - dùng cho trang "Tôi". */
    public BigDecimal[] tongThuChiToanBo(int userId) {
        String sql = "SELECT " +
                "COALESCE(SUM(CASE WHEN loai = 'THU' THEN so_tien ELSE 0 END), 0) AS tong_thu, " +
                "COALESCE(SUM(CASE WHEN loai = 'CHI' THEN so_tien ELSE 0 END), 0) AS tong_chi " +
                "FROM giao_dich WHERE user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BigDecimal[]{ rs.getBigDecimal("tong_thu"), rs.getBigDecimal("tong_chi") };
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new BigDecimal[]{ BigDecimal.ZERO, BigDecimal.ZERO };
    }

    public int demSoGiaoDich(int userId) {
        String sql = "SELECT COUNT(*) AS so_luong FROM giao_dich WHERE user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("so_luong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<GiaoDich> layGanDay(int userId, int soLuong) {
        List<GiaoDich> ds = new ArrayList<>();
        String sql = "SELECT g.*, d.ten_danh_muc FROM giao_dich g " +
                "JOIN danh_muc d ON g.danh_muc_id = d.id " +
                "WHERE g.user_id = ? ORDER BY g.ngay DESC, g.id DESC LIMIT ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, soLuong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ds.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ds;
    }

    /** Thống kê thu/chi gom nhóm theo ngày (30 ngày gần nhất) - dùng vẽ biểu đồ Chart.js. */
    public List<ThongKe> thongKeTheoNgay(int userId) {
        List<ThongKe> ds = new ArrayList<>();
        String sql = "SELECT ngay, " +
                "COALESCE(SUM(CASE WHEN loai = 'THU' THEN so_tien ELSE 0 END), 0) AS tong_thu, " +
                "COALESCE(SUM(CASE WHEN loai = 'CHI' THEN so_tien ELSE 0 END), 0) AS tong_chi " +
                "FROM giao_dich WHERE user_id = ? " +
                "AND ngay >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                "GROUP BY ngay ORDER BY ngay ASC";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThongKe tk = new ThongKe();
                    tk.setNgay(rs.getDate("ngay").toString());
                    tk.setTongThu(rs.getBigDecimal("tong_thu"));
                    tk.setTongChi(rs.getBigDecimal("tong_chi"));
                    ds.add(tk);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ds;
    }

    private GiaoDich map(ResultSet rs) throws SQLException {
        GiaoDich gd = new GiaoDich();
        gd.setId(rs.getInt("id"));
        gd.setUserId(rs.getInt("user_id"));
        gd.setDanhMucId(rs.getInt("danh_muc_id"));
        gd.setTenDanhMuc(rs.getString("ten_danh_muc"));
        gd.setLoai(rs.getString("loai"));
        gd.setSoTien(rs.getBigDecimal("so_tien"));
        gd.setNgay(rs.getDate("ngay"));
        gd.setGhiChu(rs.getString("ghi_chu"));
        return gd;
    }
}
