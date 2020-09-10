package web.servlet;

import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import service.serviceImp.CategoryServiceImp;
import service.serviceImp.ProductServiceImp;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/indexServlet")
public class IndexServlet extends BaseServlet {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        //查询全部分类
//        CategoryService CategoryService = new CategoryServiceImp();
//        List<Category> list = CategoryService.findAllCats();
//        //放入request域对象
//        req.setAttribute("allCats", list);
        ProductServiceImp productService = new ProductServiceImp();
        //获取最新9件商品
        List<Product> list01 = productService.findNews();
        //获取最热9件商品
        List<Product> list02 = productService.findHots();
        //将最新商品放入request
        req.setAttribute("news", list01);
        //将最热商品放入request
        req.setAttribute("hots", list02);


        //转发到/jsp/index.jsp
        return "/jsp/index.jsp";
    }
}
