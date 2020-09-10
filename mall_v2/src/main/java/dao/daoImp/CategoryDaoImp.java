package dao.daoImp;


import dao.CategoryDao;
import domain.Category;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;


public class CategoryDaoImp implements CategoryDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void addCategory(Category category) {
        String sql = "insert into category values ( ? ,?,?)";
        template.update(sql, category.getCid(), category.getCname(), category.getIsvalid());
    }

    @Override
    public void delCategory(String cid) throws SQLException {
        String sql = "UPDATE category SET isvalid = 0 WHERE cid =?";
        template.update(sql, cid);
    }

    @Override
    public void editCategory(Category c) throws SQLException {
        String sql = "UPDATE category SET cname =? WHERE cid =?";
        template.update(sql, c.getCname(), c.getCid());
    }

    @Override
    public int findTotalRecords(int isvalid) throws SQLException {
        String sql = "select count(*) from category where isvalid=?";
        return template.queryForObject(sql, Integer.class, isvalid);
    }

    @Override
    public List<Category> findAllCatsWithPage(int isvalid, int startIndex, int pageSize) throws SQLException {
        String sql = "select * from category where isvalid =? limit  ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class),isvalid,startIndex, pageSize);
    }

    @Override
    public List<Category> findAllCats() {
        String sql = "select * from category where isvalid=1";
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

}