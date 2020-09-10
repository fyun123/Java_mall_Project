package web.filter;

import domain.User;
import service.UserService;
import service.serviceImp.UserServiceImp;
import utils.CookUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutoLoginFilter implements Filter {

    public void destroy() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //chain.doFilter(request, response);
        //强转
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp=(HttpServletResponse) response;
        User uu = (User) req.getSession().getAttribute("loginUser");
        if (null != uu) {
            chain.doFilter(request, response);
            return;
        }
        //获取用户携带到服务端cookie对象
        Cookie ck = CookUtils.getCookieByName("autoLogin", req.getCookies());
        //获取不到,放行
        if (null == ck) {
            chain.doFilter(request, response);
            return;
        }
        //获取到,获取用户名和密码  aaa#aaaa
        String um = ck.getValue().split("#")[0];
        String up = ck.getValue().split("#")[1];
        User user = new User();
        user.setUsername(um);
        user.setPassword(up);
        UserService UserService = new UserServiceImp();
        User user02 = null;
        try {
            user02 = UserService.userLogin(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != user02) {
            //通过用户名和密码登录,登录成功:向session存放登录用户信息,放行
            req.getSession().setAttribute("loginUser", user02);
            chain.doFilter(request, response);
        } else {
            //通过用户名和密码登录,登录失败:放行
            chain.doFilter(request, response);
        }
    }
}
