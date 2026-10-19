package com.quanlychitieu.servlet;

import com.quanlychitieu.dao.GiaoDichDAO;
import com.quanlychitieu.model.GiaoDich;
import com.quanlychitieu.model.NguoiDung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("nguoiDung");

        BigDecimal[] tongThang = giaoDichDAO.tongThuChiThangHienTai(nd.getId());
        BigDecimal tongThu = tongThang[0];
        BigDecimal tongChi = tongThang[1];
        BigDecimal soDu = tongThu.subtract(tongChi);

        List<GiaoDich> ganDay = giaoDichDAO.layGanDay(nd.getId(), 5);

        req.setAttribute("tongThu", tongThu);
        req.setAttribute("tongChi", tongChi);
        req.setAttribute("soDu", soDu);
        req.setAttribute("ganDay", ganDay);

        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
}
