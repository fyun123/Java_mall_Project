package dao;



import domain.Order;
import domain.OrderItem;
import domain.User;

import java.sql.Connection;
import java.util.List;

public interface OrderDao {

	void saveOrder(Connection conn, Order order)throws Exception;

	void saveOrderItem(Connection conn, OrderItem item)throws Exception;

	int getTotalRecords(User user)throws Exception;

	List findMyOrdersWithPage(User user, int startIndex, int pageSize)throws Exception;

	Order findOrderByOid(String oid)throws Exception;

	void updateOrder(Order order)throws Exception;

	List<Order> findAllOrders(int startIndex, int pageSize)throws Exception;

	List<Order> findAllOrders(String st, int startIndex, int pageSize)throws Exception;

	int findTotalRecords() throws Exception;

	int findTotalRecords(String st) throws Exception;
}
