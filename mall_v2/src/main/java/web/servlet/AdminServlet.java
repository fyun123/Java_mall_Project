package web.servlet;


import domain.Admin;
import domain.User;
import service.AdminService;
import service.UserService;
import service.serviceImp.AdminServiceImp;
import service.serviceImp.UserServiceImp;
import utils.MyBeanUtils;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminServlet")
public class AdminServlet extends BaseServlet {
    public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Admin admin = new Admin();
        MyBeanUtils.populate(admin, request.getParameterMap());
        AdminService adminService = new AdminServiceImp();
        try {
            Admin loginAdmin = adminService.adminLogin(admin);
            //登录成功
            //存储用户信息
            session.setAttribute("loginAdmin", loginAdmin);
            //重定向
            response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
            return null;
        } catch (RuntimeException e) {
            //登录失败
            //存储提示信息到request
            String loginError = e.getMessage();
            request.setAttribute("loginError", loginError);
            return "/admin/index.jsp";
        }

    }
}
