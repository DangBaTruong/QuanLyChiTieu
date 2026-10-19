package com.quanlychitieu.servlet;

import com.quanlychitieu.dao.NguoiDungDAO;
import com.quanlychitieu.model.NguoiDung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/dang-nhap")
public class DangNhapServlet extends HttpServlet {

    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/dangnhap.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String tenDangNhap = req.getParameter("tenDangNhap");
        String matKhau = req.getParameter("matKhau");

        NguoiDung nd = nguoiDungDAO.dangNhap(tenDangNhap, matKhau);
        if (nd != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("nguoiDung", nd);
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("loi", "Tên đăng nhập hoặc mật khẩu không đúng.");
            req.getRequestDispatcher("/dangnhap.jsp").forward(req, resp);
        }
    }
}
