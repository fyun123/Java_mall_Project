package dao;

import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User findUserByUsreName(String um) throws SQLException;

    void userRegist(User user01)throws SQLException;

    User userActive(String code)throws SQLException;

    void updateUser(User user01)throws SQLException;

    User userLogin(User user01)throws SQLException;

    List<User> findAllUsers(int state, int startIndex, int pageSize) throws SQLException;

    void delUser(String uid) throws SQLException;

    void editUser(User user) throws SQLException;

    int findTotalRecords(int state) throws SQLException;
}
