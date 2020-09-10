package service.serviceImp;


import dao.AdminDao;
import domain.Admin;
import service.AdminService;
import utils.BeanFactory;
import java.sql.SQLException;

public class AdminServiceImp implements AdminService {
    AdminDao adminDao = (AdminDao) BeanFactory.createObject("AdminDao");

    @Override
    public void updateAdmin(Admin admin01) throws SQLException {

    }

    @Override
    public Admin adminLogin(Admin admin01) throws SQLException {
        Admin adminLogin = adminDao.adminLogin(admin01);
        if (adminLogin == null){
            throw new RuntimeException("管理员账号或密码错误");
        }else {
            return adminLogin;
        }
    }




}
