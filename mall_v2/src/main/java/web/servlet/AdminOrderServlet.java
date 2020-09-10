package web.servlet;

import domain.Order;
import domain.PageModel;
import net.sf.json.JSONArray;
import service.OrderService;
import service.UserService;
import service.serviceImp.OrderServiceImp;
import service.serviceImp.UserServiceImp;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/adminOrderServlet")
public class AdminOrderServlet extends BaseServlet {

    public String findAllOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取当前页
        int curNum = Integer.parseInt(req.getParameter("num"));
        String st = req.getParameter("state");
        //调用业务层查全部商品信息返回PageModel
        OrderService orderService = new OrderServiceImp();
        PageModel pm = orderService.findAllOrders(st,curNum);
//        if (null == st || "".equals(st)) {
//            //获取到全部订单
//            pm = orderService.findAllOrders(curNum);
//        } else {
//            pm = orderService.findAllOrders(st,curNum);
//        }
        //将PageModel放入request
        req.setAttribute("page", pm);
        //转发到/admin/order/list.jsp
        return "/admin/order/list.jsp";
    }

    //findOrderByOidWithAjax
    public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //服务端获取到订单ID,
        String oid = req.getParameter("oid");
        //查询这个订单下所有的订单项以及订单项对应的商品信息,返回集合
        OrderService OrderService = new OrderServiceImp();
        Order order = OrderService.findOrderByOid(oid);
        //将返回的集合转换为JSON格式字符串,响应到客户端
        String jsonStr = JSONArray.fromObject(order.getList()).toString();
        //响应到客户端
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().println(jsonStr);
        return null;
    }

    //updateOrderByOid
    public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取订单ID
        String oid = req.getParameter("oid");
        //根据订单ID查询订单
        OrderService OrderService = new OrderServiceImp();
        Order order = OrderService.findOrderByOid(oid);
        //设置订单状态
        order.setState(3);
        //修改订单信息
        OrderService.updateOrder(order);
        //重新定向到查询已发货订单
        resp.sendRedirect(req.getContextPath() + "/adminOrderServlet?method=findAllOrders&state=3");
        return null;
    }
}
