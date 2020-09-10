package service;


import domain.PageModel;
import domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User findUserByUsreName(String um) throws SQLException;

    void userRegist(User user01) throws SQLException;

    Boolean userActive(String code) throws SQLException;

    User userLogin(User user01) throws SQLException;

    PageModel findAllUsers(int state, int curNum) throws SQLException;

    void delUser(String uid) throws SQLException;

    void editUser(User user) throws SQLException;
}
