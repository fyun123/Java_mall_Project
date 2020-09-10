package service;


import domain.Admin;
import domain.User;

import java.sql.SQLException;

public interface AdminService {

    void updateAdmin(Admin admin01) throws SQLException;

    Admin adminLogin(Admin admin01) throws SQLException;

}
