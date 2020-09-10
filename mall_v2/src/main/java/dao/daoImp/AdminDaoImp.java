package dao.daoImp;


import dao.AdminDao;
import dao.UserDao;
import domain.Admin;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import service.AdminService;
import utils.JDBCUtils;

import java.sql.SQLException;

public class AdminDaoImp implements AdminDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void updateAdmin(Admin admin01) throws SQLException {

    }

    @Override
    public Admin adminLogin(Admin admin01) {
        try {
            String sql = "select * from admin where adminname =? and password=?";
            return template.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Admin.class),
                    admin01.getAdminname(),
                    admin01.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
