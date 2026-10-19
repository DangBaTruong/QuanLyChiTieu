package com.quanlychitieu.servlet;

import com.quanlychitieu.dao.GiaoDichDAO;
import com.quanlychitieu.model.NguoiDung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/toi")
public class TrangToiServlet extends HttpServlet {

    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NguoiDung nd = (NguoiDung) req.getSession().getAttribute("nguoiDung");

        BigDecimal[] tongToanBo = giaoDichDAO.tongThuChiToanBo(nd.getId());
        int soGiaoDich = giaoDichDAO.demSoGiaoDich(nd.getId());

        req.setAttribute("tongThu", tongToanBo[0]);
        req.setAttribute("tongChi", tongToanBo[1]);
        req.setAttribute("soDu", tongToanBo[0].subtract(tongToanBo[1]));
        req.setAttribute("soGiaoDich", soGiaoDich);

        req.getRequestDispatcher("/toi.jsp").forward(req, resp);
    }
}
