package dao;



import domain.Category;
import domain.User;

import java.sql.SQLException;
import java.util.List;


public interface CategoryDao {

	List<Category> findAllCats()throws SQLException;

	void addCategory(Category category) throws SQLException;

	void delCategory(String cid) throws SQLException;

	void editCategory(Category c) throws SQLException;

    int findTotalRecords(int isvalid) throws SQLException;

	List<Category> findAllCatsWithPage(int isvalid, int startIndex, int pageSize) throws SQLException;
}
