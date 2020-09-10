package web.servlet;


import domain.Category;
import domain.PageModel;
import service.CategoryService;
import service.UserService;
import service.serviceImp.CategoryServiceImp;
import service.serviceImp.UserServiceImp;
import utils.UUIDUtils;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/adminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

    //获取所有分类
    public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取当前页
        int curNum = Integer.parseInt(req.getParameter("num"));
        int isvalid = Integer.parseInt(req.getParameter("isvalid"));
        //调用业务层查全部商品信息返回PageModel
        CategoryService categoryService = new CategoryServiceImp();
        PageModel pm = categoryService.findAllCatsWithPage(isvalid, curNum);
        //将PageModel放入request
        req.setAttribute("page", pm);
        //转发到/admin/product/list.jsp
        return "/admin/category/list.jsp";
    }

    //addCategoryUI
    public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return "/admin/category/add.jsp";
    }

    //添加分类
    public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取分类名称
        String cname = req.getParameter("cname");
        //创建分类ID
        String id = UUIDUtils.getId();
        Category c = new Category();
        c.setCid(id);
        c.setCname(cname);
        c.setIsvalid(1);
        //调用业务层添加分类功能
        CategoryService categoryService = new CategoryServiceImp();
        categoryService.addCategory(c);
        //重定向到查询全部分类信息
        resp.sendRedirect(  req.getContextPath()+"/adminCategoryServlet?method=findAllCats&num=1&isvalid=1");
        return null;
    }

    //editCategoryUI
    public String editCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String cid = req.getParameter("cid");
        req.setAttribute("cid",cid);
        return "/admin/category/edit.jsp";
    }
    public String editCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取分类名称
        String cname = req.getParameter("cname");
        //创建分类ID
        String id = req.getParameter("cid");
        Category c = new Category();
        c.setCid(id);
        c.setCname(cname);
        //调用业务层添加分类功能
        CategoryService categoryService = new CategoryServiceImp();
        categoryService.editCategory(c);
        //重定向到查询全部分类信息
        resp.sendRedirect(  req.getContextPath()+"/adminCategoryServlet?method=findAllCats&num=1&isvalid=1");
        return null;
    }
    //删除分类
    public String delCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String cid = req.getParameter("cid");
        CategoryService categoryService = new CategoryServiceImp();
        categoryService.delCategory(cid);
        resp.sendRedirect(  req.getContextPath()+"/adminCategoryServlet?method=findAllCats&num=1&isvalid=1");
        return null;
    }
}
