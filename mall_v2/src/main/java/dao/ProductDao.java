package dao;

import domain.Product;

import java.util.List;

public interface ProductDao {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

	Product findProductByPid(String pid)throws Exception;

	int findTotalRecords(String cid)throws Exception;

	List findProductsByCidWithPage(String cid, int startIndex, int pageSize)throws Exception;

	void saveProduct(Product product)throws Exception;

    void editProduct(Product product) throws Exception;

	void pushDownORUpProduct(int pflag, String pid) throws Exception;

	int findTotalRecords(int pflag)throws Exception;

	List<Product> findAllProductsWithPage(int pflag, int startIndex, int pageSize)throws Exception;
}
