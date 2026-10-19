package com.quanlychitieu.servlet;

import com.quanlychitieu.dao.DanhMucDAO;
import com.quanlychitieu.model.DanhMuc;
import com.quanlychitieu.model.NguoiDung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/danh-muc")
public class DanhMucServlet extends HttpServlet {

    private final DanhMucDAO danhMucDAO = new DanhMucDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NguoiDung nd = (NguoiDung) req.getSession().getAttribute("nguoiDung");
        List<DanhMuc> danhSach = danhMucDAO.layDanhSachTheoUser(nd.getId());
        req.setAttribute("danhSachDanhMuc", danhSach);
        req.getRequestDispatcher("/danhmuc.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        NguoiDung nd = (NguoiDung) req.getSession().getAttribute("nguoiDung");
        String action = req.getParameter("action");

        if ("them".equals(action)) {
            String ten = req.getParameter("tenDanhMuc");
            String loai = req.getParameter("loai");
            if (ten != null && !ten.trim().isEmpty() && loai != null) {
                DanhMuc dm = new DanhMuc();
                dm.setTenDanhMuc(ten.trim());
                dm.setLoai(loai);
                dm.setUserId(nd.getId());
                danhMucDAO.them(dm);
            }
        } else if ("xoa".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            danhMucDAO.xoa(id, nd.getId());
        }

        resp.sendRedirect(req.getContextPath() + "/danh-muc");
    }
}
