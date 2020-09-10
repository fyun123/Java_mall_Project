package web.servlet;

import com.mysql.cj.xdevapi.JsonArray;
import domain.Category;
import redis.clients.jedis.Jedis;
import service.CategoryService;
import service.serviceImp.CategoryServiceImp;
import utils.JedisPoolUtils;
import net.sf.json.JSONArray;
import web.servlet.BaseServlet.BaseServlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseServlet {

    public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		response.setContentType("application/json;charset=utf-8");
//		CategoryService categoryService = new CategoryServiceImp();
//		List<Category> allCats = categoryService.findAllCats();
//		String string = JSONArray.fromObject(allCats).toString();
//		response.getWriter().println(string);
        response.setContentType("application/json;charset=utf-8");
        Jedis jedis = JedisPoolUtils.getJedis();
        String allCats = jedis.get("allCats");
        if (null == allCats || "".equals(allCats)) {
            System.out.println("缓存中没有数据");
            //查询所有分类
            CategoryService CategoryService = new CategoryServiceImp();
            List<Category> list = CategoryService.findAllCats();
            //将集合中的所有分类信息转换为JSON格式的字符串数据
            allCats = JSONArray.fromObject(list).toString();
            jedis.set("allCats", allCats);
        } else {
            System.out.println("缓存中有数据");
        }
        //将字符串数据响应到客户端
        response.getWriter().println(allCats);
        jedis.close();
        return null;
    }
}