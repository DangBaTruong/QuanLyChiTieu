package com.quanlychitieu.servlet;

import com.quanlychitieu.dao.DanhMucDAO;
import com.quanlychitieu.dao.GiaoDichDAO;
import com.quanlychitieu.model.DanhMuc;
import com.quanlychitieu.model.GiaoDich;
import com.quanlychitieu.model.NguoiDung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet("/giao-dich")
public class GiaoDichServlet extends HttpServlet {

    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();
    private final DanhMucDAO danhMucDAO = new DanhMucDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NguoiDung nd = (NguoiDung) req.getSession().getAttribute("nguoiDung");

        String loaiLoc = req.getParameter("loai"); // "ALL", "THU", "CHI" hoặc null
        String danhMucIdParam = req.getParameter("danhMucId");
        int danhMucIdLoc = 0;
        if (danhMucIdParam != null && !danhMucIdParam.isEmpty()) {
            try {
                danhMucIdLoc = Integer.parseInt(danhMucIdParam);
            } catch (NumberFormatException ignored) {}
        }

        List<GiaoDich> danhSach = giaoDichDAO.layDanhSach(nd.getId(), loaiLoc, danhMucIdLoc);
        List<DanhMuc> danhSachDanhMuc = danhMucDAO.layDanhSachTheoUser(nd.getId());

        req.setAttribute("danhSachGiaoDich", danhSach);
        req.setAttribute("danhSachDanhMuc", danhSachDanhMuc);
        req.setAttribute("loaiLoc", loaiLoc == null ? "ALL" : loaiLoc);
        req.setAttribute("danhMucIdLoc", danhMucIdLoc);

        req.getRequestDispatcher("/giaodich.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        NguoiDung nd = (NguoiDung) req.getSession().getAttribute("nguoiDung");
        String action = req.getParameter("action");

        try {
            if ("them".equals(action)) {
                GiaoDich gd = new GiaoDich();
                gd.setUserId(nd.getId());
                gd.setDanhMucId(Integer.parseInt(req.getParameter("danhMucId")));
                gd.setLoai(req.getParameter("loai"));
                gd.setSoTien(new BigDecimal(req.getParameter("soTien")));
                gd.setNgay(Date.valueOf(req.getParameter("ngay")));
                gd.setGhiChu(req.getParameter("ghiChu"));
                giaoDichDAO.them(gd);

            } else if ("sua".equals(action)) {
                GiaoDich gd = new GiaoDich();
                gd.setId(Integer.parseInt(req.getParameter("id")));
                gd.setUserId(nd.getId());
                gd.setDanhMucId(Integer.parseInt(req.getParameter("danhMucId")));
                gd.setLoai(req.getParameter("loai"));
                gd.setSoTien(new BigDecimal(req.getParameter("soTien")));
                gd.setNgay(Date.valueOf(req.getParameter("ngay")));
                gd.setGhiChu(req.getParameter("ghiChu"));
                giaoDichDAO.capNhatGiaoDich(gd);

            } else if ("xoa".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                giaoDichDAO.xoa(id, nd.getId());
            }
        } catch (Exception e) {
            req.getSession().setAttribute("loi", "Dữ liệu không hợp lệ: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/giao-dich");
    }
}
