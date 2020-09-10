package service;


import domain.Category;
import domain.PageModel;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {


	void addCategory(Category c)throws Exception;

	List<Category> findAllCats() throws Exception;

	void delCategory(String cid) throws Exception;

	void editCategory(Category c)throws Exception;

	PageModel findAllCatsWithPage(int isvalid, int curNum) throws Exception;
}
