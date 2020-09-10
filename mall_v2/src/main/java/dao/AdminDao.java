package dao;

import domain.Admin;
import domain.User;

import java.sql.SQLException;

public interface AdminDao {

    void updateAdmin(Admin admin01)throws SQLException;

    Admin adminLogin(Admin admin01)throws SQLException;

}
