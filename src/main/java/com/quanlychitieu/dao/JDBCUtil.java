package com.quanlychitieu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    // TODO: chỉnh lại URL/user/password cho đúng với MySQL trên máy bạn
    private static final String URL = "jdbc:mysql://localhost:3306/quanlychitieu?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            // Đăng ký driver JDBC - nếu thiếu dòng này khi deploy trên Tomcat
            // rất dễ gặp lỗi "No suitable driver found"
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy MySQL JDBC Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
