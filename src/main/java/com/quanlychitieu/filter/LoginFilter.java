package com.quanlychitieu.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Chặn truy cập các trang cần đăng nhập (dashboard, danh mục, giao dịch,
 * thống kê, trang tôi) nếu session chưa có "nguoiDung" -> chuyển hướng
 * về trang đăng nhập.
 */
@WebFilter(urlPatterns = {
        "/dashboard", "/danh-muc", "/giao-dich", "/thong-ke", "/toi"
})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("nguoiDung") == null) {
            resp.sendRedirect(req.getContextPath() + "/dang-nhap");
            return;
        }
        chain.doFilter(request, response);
    }
}
