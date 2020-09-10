package service.serviceImp;

import dao.CategoryDao;
import domain.Category;
import domain.PageModel;
import domain.User;
import service.CategoryService;
import utils.BeanFactory;
import redis.clients.jedis.Jedis;
import utils.JedisPoolUtils;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImp implements CategoryService {

	CategoryDao categoryDao=(CategoryDao)BeanFactory.createObject("CategoryDao");
	


	@Override
	public void addCategory(Category c) throws Exception {
		//本质是向MYSQL插入一条数据
		categoryDao.addCategory(c);
		//更新redis缓存
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.del("allCats");
		jedis.close();
	}

	@Override
	public List<Category> findAllCats() throws SQLException {
		return categoryDao.findAllCats();
	}

	@Override
	public void delCategory(String cid) throws Exception {
		categoryDao.delCategory(cid);
		//更新redis缓存
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.del("allCats");
		jedis.close();
	}

	@Override
	public void editCategory(Category c) throws Exception {
		categoryDao.editCategory(c);
		//更新redis缓存
		Jedis jedis = JedisPoolUtils.getJedis();
		jedis.del("allCats");
		jedis.close();
	}

	@Override
	public PageModel findAllCatsWithPage(int isvalid, int curNum) throws Exception {
		//1_创建对象
		int totalRecords = categoryDao.findTotalRecords(isvalid);
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		//2_关联集合 select * from category where isvalid=? limit ? , ?
		List<Category> list = categoryDao.findAllCatsWithPage(isvalid, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("adminCategoryServlet?method=findAllCats&isvalid="+isvalid);
		return pm;
	}


}
