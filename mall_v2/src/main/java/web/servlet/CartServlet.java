package web.servlet;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import service.ProductService;
import service.serviceImp.ProductServiceImp;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cartServlet")
public class CartServlet extends BaseServlet {
    public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //从session中获取购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        //获取商品pid和数量
        String pid = request.getParameter("pid");
        int num = Integer.parseInt(request.getParameter("quantity"));
        //查询pid的商品
        ProductService productService = new ProductServiceImp();
        Product product = productService.findProductByPid(pid);
        //添加购物项
        CartItem cartItem = new CartItem();
        cartItem.setNum(num);
        //添加购物项到购物车
        cartItem.setProduct(product);
        cart.addCartItemToCar(cartItem);
        //重定向到/jsp/cart.jsp
        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
        return null;
    }

    public String delCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取删除商品的pid
        String id = request.getParameter("pid");
        //获取购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.removeCartItem(id);
        //重定向到/jsp/cart.jsp
        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
        return null;
    }

    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clearCart();
        //重定向到/jsp/cart.jsp
        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
        return null;
    }
}
