package web.servlet;


import domain.Category;
import domain.PageModel;
import domain.User;
import service.CategoryService;
import service.ProductService;
import service.UserService;
import service.serviceImp.CategoryServiceImp;
import service.serviceImp.ProductServiceImp;
import service.serviceImp.UserServiceImp;
import utils.BeanFactory;
import utils.MyBeanUtils;
import utils.UUIDUtils;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@WebServlet("/adminUserServlet")
public class AdminUserServlet extends BaseServlet {

    //获取所有用户
    public String findAllUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取当前页
        int curNum = Integer.parseInt(req.getParameter("num"));
        int state = Integer.parseInt(req.getParameter("state"));
        //调用业务层查全部用户信息返回PageModel
        UserService userService = new UserServiceImp();
        PageModel pm = userService.findAllUsers(state, curNum);
        //将PageModel放入request
        req.setAttribute("page", pm);
        //转发到/admin/product/list.jsp
        return "/admin/user/list.jsp";
    }

    //editUserUI
    public String editUserUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User();
        MyBeanUtils.populate(user, req.getParameterMap());
        req.setAttribute("u",user);
        return "/admin/user/edit.jsp" ;
    }
    public String editUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User();
        MyBeanUtils.populate(user, req.getParameterMap());
        UserService userService = new UserServiceImp();
        userService.editUser(user);
        System.out.println(user);
        //重定向到查询全部用户信息
        resp.sendRedirect(  req.getContextPath()+"/adminUserServlet?method=findAllUsers&num=1&state="+user.getState());
        return null;
    }
    //删除用户
    public String delUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String uid = req.getParameter("uid");
        int state = Integer.parseInt(req.getParameter("state"));
        UserService userService = new UserServiceImp();
        userService .delUser(uid);
        resp.sendRedirect(  req.getContextPath()+"/adminUserServlet?method=findAllUsers&num=1&state="+state);
        return null;
    }
}
