package com.quanlychitieu.servlet;

import com.google.gson.Gson;
import com.quanlychitieu.dao.GiaoDichDAO;
import com.quanlychitieu.model.NguoiDung;
import com.quanlychitieu.model.ThongKe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/thong-ke")
public class ThongKeServlet extends HttpServlet {

    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NguoiDung nd = (NguoiDung) req.getSession().getAttribute("nguoiDung");

        List<ThongKe> danhSachThongKe = giaoDichDAO.thongKeTheoNgay(nd.getId());
        BigDecimal[] tongThang = giaoDichDAO.tongThuChiThangHienTai(nd.getId());

        req.setAttribute("danhSachThongKe", danhSachThongKe);
        req.setAttribute("tongThuThang", tongThang[0]);
        req.setAttribute("tongChiThang", tongThang[1]);

        // Chuyển sang JSON để nhúng thẳng vào script Chart.js trong JSP
        req.setAttribute("nhanNgayJson", gson.toJson(danhSachThongKe.stream().map(ThongKe::getNgay).toArray()));
        req.setAttribute("dataThuJson", gson.toJson(danhSachThongKe.stream().map(ThongKe::getTongThu).toArray()));
        req.setAttribute("dataChiJson", gson.toJson(danhSachThongKe.stream().map(ThongKe::getTongChi).toArray()));

        req.getRequestDispatcher("/thongke.jsp").forward(req, resp);
    }
}
