package dao.daoImp;

import dao.ProductDao;
import domain.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;

public class ProductDaoImp implements ProductDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void saveProduct(Product product) {
        String sql = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(), product.getCid()};
        template.update(sql, params);
    }

    @Override
    public void editProduct(Product product) throws Exception {
        String sql = "UPDATE product SET pname=?, market_price=?, shop_price=?, pimage=?, pdate=?, is_hot=?, pdesc=?, cid=? where pid=?";
        Object[] params = {product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getCid(), product.getPid()};
        template.update(sql, params);
    }

    @Override
    public void pushDownORUpProduct(int pflag, String pid) throws Exception {
        String sql = "UPDATE product SET pflag =? WHERE pid =?";
        template.update(sql, pflag, pid);
    }

    @Override
    public List<Product> findAllProductsWithPage(int pflag, int startIndex, int pageSize) {
        String sql = "select * from product where pflag=? order by pdate desc limit  ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class), pflag, startIndex, pageSize);
    }

    @Override
    public int findTotalRecords(int pflag) {
        String sql = "select count(*) from product where pflag=?";
        return template.queryForObject(sql, Integer.class, pflag);
    }

    @Override
    public Product findProductByPid(String pid) {
        String sql = "select * from product where pid=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), pid);
    }

    @Override
    public List<Product> findHots() {
        String sql = "SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0 ,9 ";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public List<Product> findNews() {
        String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0 , 9 ";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public List findProductsByCidWithPage(String cid, int startIndex, int pageSize) {
        String sql = "select * from product where cid=? and pflag=0 limit ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class), cid, startIndex, pageSize);
    }

    @Override
    public int findTotalRecords(String cid) {
        String sql = "select count(*) from product where cid =? and pflag=0";
        return template.queryForObject(sql, Integer.class, cid);
    }

}
