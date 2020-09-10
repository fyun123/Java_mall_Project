package service.serviceImp;

import dao.OrderDao;
import dao.UserDao;
import dao.daoImp.OrderDaoImp;
import domain.Order;
import domain.OrderItem;
import domain.PageModel;
import domain.User;
import service.OrderService;
import utils.BeanFactory;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImp implements OrderService {

    OrderDao orderDao = (OrderDao) BeanFactory.createObject("OrderDao");


    @Override
    public PageModel findAllOrders(String state,int curNum) throws Exception {
        //1_创建对象
        int totalRecords;
        PageModel pm;
        if(null == state || "".equals(state)){
            totalRecords = orderDao.findTotalRecords();
        }else{
            totalRecords = orderDao.findTotalRecords(state);
        }
        pm = new PageModel(curNum, totalRecords, 5);
        //2_关联集合 select * from orders where state= ? limit ? , ?
        List<Order> list = null;
        if(null == state || "".equals(state)){
            list = orderDao.findAllOrders(pm.getStartIndex(), pm.getPageSize());
        }else{
            list = orderDao.findAllOrders(state, pm.getStartIndex(), pm.getPageSize());
        }

        pm.setList(list);
        //3_关联url
        if(null == state || "".equals(state)){
            pm.setUrl("adminOrderServlet?method=findAllOrders");
        }else{
            pm.setUrl("adminOrderServlet?method=findAllOrders&state="+state);
        }

        return pm;
    }

    @Override
    public void saveOrder(Order order) throws SQLException {
        //保存订单和订单下所有的订单项(同时成功,失败)
		/*try {
			JDBCUtils.startTransaction();
			OrderDao orderDao=new OrderDaoImp();
			orderDao.saveOrder(order);
			for(OrderItem item:order.getList()){
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
		}
		*/

        Connection conn = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            //保存订单

            orderDao.saveOrder(conn, order);
            //保存订单项
            for (OrderItem item : order.getList()) {
                orderDao.saveOrderItem(conn, item);
            }
            //提交
            conn.commit();
        } catch (Exception e) {
            //回滚
            conn.rollback();
        }
    }

    @Override
    public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
        //1_创建PageModel对象,目的:计算并且携带分页参数
        //select count(*) from orders where uid=?
        int totalRecords = orderDao.getTotalRecords(user);
        PageModel pm = new PageModel(curNum, totalRecords, 3);
        //2_关联集合  select * from orders where uid=? limit ? ,?
        List list = orderDao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
        pm.setList(list);
        //3_关联url
        pm.setUrl("orderServlet?method=findMyOrdersWithPage");
        return pm;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {
        return orderDao.findOrderByOid(oid);
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        orderDao.updateOrder(order);
    }
}