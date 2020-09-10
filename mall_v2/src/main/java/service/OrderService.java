package service;

import domain.Order;
import domain.PageModel;
import domain.User;

import java.util.List;

public interface OrderService {

	void saveOrder(Order order)throws Exception;

	PageModel findMyOrdersWithPage(User user, int curNum)throws Exception;

	Order findOrderByOid(String oid)throws Exception;

	void updateOrder(Order order)throws Exception;

	PageModel findAllOrders(String st, int curNum)throws Exception;

}
