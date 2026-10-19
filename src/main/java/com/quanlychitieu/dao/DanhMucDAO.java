package com.quanlychitieu.dao;

import com.quanlychitieu.model.DanhMuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {

    public List<DanhMuc> layDanhSachTheoUser(int userId) {
        List<DanhMuc> ds = new ArrayList<>();
        String sql = "SELECT * FROM danh_muc WHERE user_id = ? ORDER BY loai, ten_danh_muc";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
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

    public List<DanhMuc> layTheoLoai(int userId, String loai) {
        List<DanhMuc> ds = new ArrayList<>();
        String sql = "SELECT * FROM danh_muc WHERE user_id = ? AND loai = ? ORDER BY ten_danh_muc";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, loai);
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

    public DanhMuc layTheoId(int id) {
        String sql = "SELECT * FROM danh_muc WHERE id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean them(DanhMuc dm) {
        String sql = "INSERT INTO danh_muc (ten_danh_muc, loai, user_id) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getLoai());
            ps.setInt(3, dm.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean xoa(int id, int userId) {
        // Kèm điều kiện user_id để tránh user này xóa danh mục của user khác
        String sql = "DELETE FROM danh_muc WHERE id = ? AND user_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DanhMuc map(ResultSet rs) throws SQLException {
        DanhMuc dm = new DanhMuc();
        dm.setId(rs.getInt("id"));
        dm.setTenDanhMuc(rs.getString("ten_danh_muc"));
        dm.setLoai(rs.getString("loai"));
        dm.setUserId(rs.getInt("user_id"));
        return dm;
    }
}
