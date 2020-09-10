package web.servlet;

import domain.PageModel;
import domain.Product;
import service.ProductService;
import service.serviceImp.ProductServiceImp;
import web.servlet.BaseServlet.BaseServlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/productServlet")
public class ProductServlet extends BaseServlet {
    public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取商品pid
        String pid = request.getParameter("pid");
        //根据商品pid查询商品信息
        ProductService productService = new ProductServiceImp();
        Product product = productService.findProductByPid(pid);
        //将获取到的商品放入request
        request.setAttribute("product", product);
        //转发到/jsp/product_info.jsp
        return "/jsp/product_info.jsp";
    }

    //findProductsByCidWithPage
    public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取cid,num
        String cid = request.getParameter("cid");
        int curNum = Integer.parseInt(request.getParameter("num"));
        //调用业务层功能:以分页形式查询当前类别下商品信息
        //返回PageModel对象(1_当前页商品信息2_分页3_url)
        ProductService ProductService = new ProductServiceImp();
        PageModel pm = ProductService.findProductsByCidWithPage(cid, curNum);
        //将PageModel对象放入request
        request.setAttribute("page", pm);
        //转发到/jsp/product_list.jsp
        return "/jsp/product_list.jsp";
    }

}
