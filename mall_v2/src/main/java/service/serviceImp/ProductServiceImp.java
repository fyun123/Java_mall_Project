package service.serviceImp;

import dao.ProductDao;
import domain.PageModel;
import domain.Product;
import service.ProductService;
import utils.BeanFactory;

import java.util.List;

public class ProductServiceImp implements ProductService {

    ProductDao productDao = (ProductDao) BeanFactory.createObject("ProductDao");

    @Override
    public void saveProduct(Product product) throws Exception {
        productDao.saveProduct(product);
    }

    @Override
    public void editProduct(Product product) throws Exception {
        productDao.editProduct(product);
    }

    @Override
    public void pushDownORUpProduct(int pflag, String pid) throws Exception {
        productDao.pushDownORUpProduct(pflag, pid);
    }

    @Override
    public Product findProductByPid(String pid) throws Exception {
        return productDao.findProductByPid(pid);
    }

    @Override
    public List<Product> findHots() throws Exception {
        return productDao.findHots();
    }

    @Override
    public List<Product> findNews() throws Exception {
        return productDao.findNews();
    }

    @Override
    public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
        //1_创建PageModel对象 目的:计算分页参数
        //统计当前分类下商品个数  select count(*) from product where cid=? and pflag=0
        int totalRecords = productDao.findTotalRecords(cid);
        PageModel pm = new PageModel(curNum, totalRecords, 12);
        //2_关联集合 select * from product where cid =? and pflag=0 limit ? ,?
        List list = productDao.findProductsByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());
        pm.setList(list);
        //3_关联url
        pm.setUrl("productServlet?method=findProductsByCidWithPage&cid=" + cid);
        return pm;
    }

    @Override
    public PageModel findAllProductsWithPage(int pflag, int curNum) throws Exception {
        //1_创建对象
        int totalRecords = productDao.findTotalRecords(pflag);
        PageModel pm = new PageModel(curNum, totalRecords, 5);
        //2_关联集合 select * from where pflag=? product limit ? , ?
        List<Product> list = productDao.findAllProductsWithPage(pflag, pm.getStartIndex(), pm.getPageSize());
        pm.setList(list);
        //3_关联url
        pm.setUrl("adminProductServlet?method=findAllProductsWithPage&pflag="+pflag);
        return pm;
    }

}
