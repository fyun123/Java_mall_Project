package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.User;
import service.UserService;
import service.serviceImp.UserServiceImp;
import utils.CookUtils;
import utils.MyBeanUtils;
import utils.UUIDUtils;
import web.servlet.BaseServlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {
    //注册页面
    public String registUI(HttpServletRequest request, HttpServletResponse response) {
        return "/jsp/register.jsp";
    }

    //判断用户名是否存在
    public void userExists(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("application/json;charset=utf-8");
        String username = request.getParameter("username");
        HashMap<String, Object> map = new HashMap<>();
        UserService userService = new UserServiceImp();
        User user = userService.findUserByUsreName(username);
        if (user != null) {
            map.put("userExsit", true);
            map.put("msg", "此用户名太受欢迎，请更换一个");
        } else {
            map.put("userExsit", false);
            map.put("msg", "用户名可用");
        }
        //将map转为json
        ObjectMapper objectMapper = new ObjectMapper();
        //并且传递到客户端
        objectMapper.writeValue(response.getWriter(), map);
    }

    //用户注册
    public String userRegist(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        //1、获取输入验证码参数
        String inputCheckCode = request.getParameter("checkCode");
        //2、获取生成验证码
        HttpSession session = request.getSession();
        String checkCodeSession = (String) session.getAttribute("checkCodeSession");
        //3、移除session中存储的验证码
        session.removeAttribute("checkCodeSession");
        //验证码输入正确，接收用户数据，开始注册
        if (checkCodeSession != null && checkCodeSession.equalsIgnoreCase(inputCheckCode)) {
            //1_接受用户数据,
            User user = MyBeanUtils.populate(User.class, request.getParameterMap());
            //2_部分数据是通过程序来设置的:uid,state,code
            user.setUid(UUIDUtils.getId());
            user.setState(0);
            user.setCode(UUIDUtils.getUUID64());
            //3_调用业务层注册功能,向用户邮箱发送一份激活邮件
            UserService userService = new UserServiceImp();
            userService.userRegist(user);
            //4_向客户端提示:用户注册成功,请激活,转发到提示页面
            request.setAttribute("msg", "用户注册成功,请激活!");
            return "/jsp/info.jsp";
        } else {
            //存储提示信息到request
            request.setAttribute("registCheckCodeError", "验证码错误");
            return "/jsp/register.jsp";
        }
    }

    //用户激活
    public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        //服务端获取到激活码,和数据库中已经存在的激活码对比,如果存在,激活成功,更改用户激活状态1,转发到登录页面,提示:激活成功,请登录.
        String code = request.getParameter("code");
        //调用业务层功能:根据激活码查询用户 select * from user where code=?
        UserService userService = new UserServiceImp();
        Boolean flag = userService.userActive(code);
        //如果用户不为空,激活码正确的,可以激活
        if (flag) {
            //转发到登录页面,提示:激活成功,请登录
            request.setAttribute("msg", "用户激活成功,请登录");
            return "/jsp/login.jsp";
        } else {
            //用户激活失败,向request放入提示信息,转发到提示页面
            request.setAttribute("msg", "用户激活失败,请重新激活!");
            return "/jsp/info.jsp";
        }
    }

    //登录页面
    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Cookie remUser = CookUtils.getCookieByName("remUser", request.getCookies());
        if (remUser != null)
            request.setAttribute("remUser", remUser.getValue());
        return "/jsp/login.jsp";
    }

    //用户登录
    public String userLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        request.setCharacterEncoding("utf-8");
        //1、获取输入验证码参数
        String inputCheckCode = request.getParameter("loginCheckCode");
        //2、获取生成验证码
        HttpSession session = request.getSession();
        String checkCodeSession = (String) session.getAttribute("checkCodeSession");
        //3、移除session中存储的验证码
        session.removeAttribute("checkCodeSession");
        //验证码输入正确，接收用户数据，开始登录
        if (checkCodeSession != null && checkCodeSession.equalsIgnoreCase(inputCheckCode)) {
            User user = new User();
            MyBeanUtils.populate(user, request.getParameterMap());
            UserService userService = new UserServiceImp();
            try {
                User loginUser = userService.userLogin(user);
                //登录成功
                //存储用户信息
                session.setAttribute("loginUser", loginUser);
                //在登录成功的基础上,判断用户是否选中自动登录复选框
                String autoLogin = request.getParameter("autoLogin");
                if ("yes".equals(autoLogin)) {
                    //用户选中自动登录复选框
                    Cookie ck = new Cookie("autoLogin", user.getUsername() + "#" + user.getPassword());
                    ck.setPath("/mall");
                    ck.setMaxAge(23423424);
                    response.addCookie(ck);
                }
                //remUser
                String remUser = request.getParameter("remUser");
                if ("yes".equals(remUser)) {
                    //用户选中记住用户名复选框
                    Cookie ck = new Cookie("remUser", user.getUsername());
                    ck.setPath("/mall");
                    ck.setMaxAge(23423424);
                    response.addCookie(ck);
                }
                //重定向
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return null;
            } catch (RuntimeException e) {
                //登录失败
                //存储提示信息到request
                String loginError = e.getMessage();
                request.setAttribute("loginError", loginError);
                return "/jsp/login.jsp";
            }
        } else {
            //存储提示信息到request
            request.setAttribute("registCheckCodeError", "验证码错误");
            return "/jsp/login.jsp";
        }
    }

    //用户退出登录
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("loginUser");
        Cookie ck = CookUtils.getCookieByName("autoLogin", request.getCookies());
        if (null != ck) {
            ck.setMaxAge(0);
            ck.setPath("/mall");
            response.addCookie(ck);
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return null;
    }
}
