package service;

import domain.PageModel;
import domain.Product;

import java.util.List;

public interface ProductService {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

	Product findProductByPid(String pid)throws Exception;

	PageModel findProductsByCidWithPage(String cid, int curNum)throws Exception;

	PageModel findAllProductsWithPage(int pflag, int curNum)throws Exception;

	void saveProduct(Product product)throws Exception;;

	void editProduct(Product product)throws Exception;

	void pushDownORUpProduct(int pflag, String pid) throws Exception;

}
