package dao.daoImp;


import dao.UserDao;
import domain.Category;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImp implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User userLogin(User user01) {
        try {
            String sql = "select * from user where username =? and password=?";
            return template.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    user01.getUsername(),
                    user01.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAllUsers(int state,int startIndex, int pageSize) throws SQLException {
        String sql = "select * from user where state =? limit  ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(User.class),state,startIndex, pageSize);
    }

    @Override
    public void delUser(String uid) throws SQLException {
        String sql = "delete from user WHERE uid =?";
        template.update(sql, uid);
    }

    @Override
    public void editUser(User user) throws SQLException {
        String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,sex=? where uid=?";
        Object[] params = {user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(), user.getSex(),user.getUid()};
        template.update(sql, params);
    }

    @Override
    public int findTotalRecords(int state) throws SQLException {
        String sql = "select count(*) from user where state=?";
        return template.queryForObject(sql, Integer.class, state);
    }

    @Override
    public User findUserByUsreName(String um) {

        try {
            //1、编写sql
            String sql = "select * from user where username=?";
            //2、调用query方法
            return template.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    um);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void userRegist(User user) {
        try {
            String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode()};
            template.update(sql, params);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User userActive(String code) {
        String sql = "select * from user where code =?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);

    }

    @Override
    public void updateUser(User user01) {
        String sql = "UPDATE USER SET username= ? ,PASSWORD=? ,NAME =? ,email =? ,telephone =? , birthday =?  ,sex =? ,state= ? , CODE = ? WHERE uid=?";
        Object[] params = {user01.getUsername(), user01.getPassword(), user01.getName(), user01.getEmail(), user01.getTelephone(), user01.getBirthday(), user01.getSex(), user01.getState(), user01.getCode(), user01.getUid()};
        template.update(sql, params);
    }
}
