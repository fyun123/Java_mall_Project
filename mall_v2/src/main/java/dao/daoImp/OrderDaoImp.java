package dao.daoImp;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.User;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class OrderDaoImp implements OrderDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Order> findAllOrders(int startIndex, int pageSize) {
        String sql = "select * from orders limit ?, ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class), startIndex, pageSize);

    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET ordertime=? ,total=? ,state= ?, address=?,NAME=?, telephone =? WHERE oid=?";
        Object[] params = {order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid()};
        template.update(sql, params);
    }

    @Override
    public List<Order> findAllOrders(String st,int startIndex, int pageSize) {
        String sql = "select * from orders where state= ? limit ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class), st,startIndex, pageSize);
    }

    @Override
    public int findTotalRecords() throws Exception {
        String sql = "select count(*) from orders";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public int findTotalRecords(String state) throws Exception {
        String sql = "select count(*) from orders where state=?";
        return template.queryForObject(sql, Integer.class, state);
    }

    @Override
    public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
        String sql = "select * from orders where uid=? limit ? , ?";
        List<Order> list = template.query(sql, new BeanPropertyRowMapper<>(Order.class), user.getUid(), startIndex, pageSize);

        //遍历所有订单
        for (Order order : list) {
            //获取到每笔订单oid   查询每笔订单下的订单项以及订单项对应的商品信息
            String oid = order.getOid();
            sql = "select * from orderItem o ,product p where o.pid=p.pid and oid=?";
            List<Map<String, Object>> list01 = template.queryForList(sql, oid);
            //遍历list
            for (Map<String, Object> map : list01) {
                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                // 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
                // 1_创建时间类型的转换器
                DateConverter dt = new DateConverter();
                // 2_设置转换的格式
                dt.setPattern("yyyy-MM-dd");
                // 3_注册转换器
                ConvertUtils.register(dt, java.util.Date.class);

                //将map中属于orderItem的数据自动填充到orderItem对象上
                BeanUtils.populate(orderItem, map);
                //将map中属于product的数据自动填充到product对象上
                BeanUtils.populate(product, map);

                //让每个订单项和商品发生关联关系
                orderItem.setProduct(product);
                //将每个订单项存入订单下的集合中
                order.getList().add(orderItem);
            }
        }
        return list;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {
        String sql = "select * from orders where oid= ?";
        Order order = (Order) template.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), oid);
        //根据订单id查询订单下所有的订单项以及订单项对应的商品信息
        sql = "select * from orderitem o, product p where o.pid=p.pid and oid=?";
        List<Map<String, Object>> list02 = template.queryForList(sql, oid);
        //遍历list
        for (Map<String, Object> map : list02) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            // 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
            // 1_创建时间类型的转换器
            DateConverter dt = new DateConverter();
            // 2_设置转换的格式
            dt.setPattern("yyyy-MM-dd");
            // 3_注册转换器
            ConvertUtils.register(dt, java.util.Date.class);

            //将map中属于orderItem的数据自动填充到orderItem对象上
            BeanUtils.populate(orderItem, map);
            //将map中属于product的数据自动填充到product对象上
            BeanUtils.populate(product, map);

            //让每个订单项和商品发生关联关系
            orderItem.setProduct(product);
            //将每个订单项存入订单下的集合中
            order.getList().add(orderItem);
        }
        return order;
    }

    @Override
    public int getTotalRecords(User user) {
        String sql = "select count(*) from orders where uid=?";
        return template.queryForObject(sql, Integer.class, user.getUid());
    }

    @Override
    public void saveOrder(Connection conn, Order order) {
        String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
        Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid()};
        template.update(sql, params);
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem item) {
        String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
        Object[] params = {item.getItemid(), item.getQuantity(), item.getTotal(), item.getProduct().getPid(), item.getOrder().getOid()};
        template.update(sql, params);
    }

}
