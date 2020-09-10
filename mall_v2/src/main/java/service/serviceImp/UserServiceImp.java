package service.serviceImp;


import dao.UserDao;
import domain.PageModel;
import domain.Product;
import domain.User;
import service.UserService;
import utils.BeanFactory;
import utils.MailUtils;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImp implements UserService {
    UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");

    @Override
    public User userLogin(User user01) throws SQLException {
        User userLogin = userDao.userLogin(user01);
        if (userLogin == null){
            throw new RuntimeException("用户或密码错误");
        }else if (userLogin.getState() == 0){
            throw new RuntimeException("用户未激活");
        }else {
            return userLogin;
        }
    }

    @Override
    public PageModel findAllUsers(int state,int curNum) throws SQLException {
        //1_创建对象
        int totalRecords = userDao.findTotalRecords(state);
        PageModel pm = new PageModel(curNum, totalRecords, 5);
        //2_关联集合 select * from user where state=? product limit ? , ?
        List<User> list = userDao.findAllUsers(state, pm.getStartIndex(), pm.getPageSize());
        pm.setList(list);
        //3_关联url
        pm.setUrl("adminUserServlet?method=findAllUsers&state="+state);
        return pm;
    }

    @Override
    public void delUser(String uid) throws SQLException {
        userDao.delUser(uid);
    }

    @Override
    public void editUser(User user) throws SQLException {
        userDao.editUser(user);
    }

    @Override
    public User findUserByUsreName(String um) throws SQLException {

        return userDao.findUserByUsreName(um);

    }

    @Override
    public void userRegist(User user01) throws SQLException {
        //3_调用业务层注册功能
        userDao.userRegist(user01);
        try {
            //向用户邮箱发送一份激活邮件
            //MailUtils.sendMail(user01.getEmail(), user01.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean userActive(String code) throws SQLException {
        User user01 = userDao.userActive(code);
        if (user01 != null){
            //可以根据激活码查询到一个用户
            //修改用户的状态,清除激活码
            user01.setState(1);
            user01.setCode(null);
            userDao.updateUser(user01);
            return true;
        }else {
            //激活码不存在
            return false;
        }
    }


}
