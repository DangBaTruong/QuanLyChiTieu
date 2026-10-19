package com.quanlychitieu.servlet;

import com.quanlychitieu.dao.NguoiDungDAO;
import com.quanlychitieu.model.NguoiDung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dang-ky")
public class DangKyServlet extends HttpServlet {

    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/dangky.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String tenDangNhap = req.getParameter("tenDangNhap");
        String matKhau = req.getParameter("matKhau");
        String xacNhanMatKhau = req.getParameter("xacNhanMatKhau");
        String hoTen = req.getParameter("hoTen");
        String email = req.getParameter("email");

        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()
                || matKhau == null || matKhau.trim().isEmpty()) {
            req.setAttribute("loi", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            req.getRequestDispatcher("/dangky.jsp").forward(req, resp);
            return;
        }
        if (!matKhau.equals(xacNhanMatKhau)) {
            req.setAttribute("loi", "Mật khẩu xác nhận không khớp.");
            req.getRequestDispatcher("/dangky.jsp").forward(req, resp);
            return;
        }
        if (nguoiDungDAO.tonTaiTenDangNhap(tenDangNhap)) {
            req.setAttribute("loi", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác.");
            req.getRequestDispatcher("/dangky.jsp").forward(req, resp);
            return;
        }

        NguoiDung nd = new NguoiDung();
        nd.setTenDangNhap(tenDangNhap.trim());
        nd.setMatKhau(matKhau);
        nd.setHoTen(hoTen);
        nd.setEmail(email);

        boolean thanhCong = nguoiDungDAO.dangKy(nd);
        if (thanhCong) {
            req.setAttribute("thongBao", "Đăng ký thành công! Vui lòng đăng nhập.");
            req.getRequestDispatcher("/dangnhap.jsp").forward(req, resp);
        } else {
            req.setAttribute("loi", "Đăng ký thất bại, vui lòng thử lại.");
            req.getRequestDispatcher("/dangky.jsp").forward(req, resp);
        }
    }
}
