package utils;


import dao.CategoryDao;
import domain.Category;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class BeanFactory {
	public static Object createObject(String name){
		try {
			//获取到read对象
			SAXReader read=new SAXReader();
			//通过read获取到一个Document对象,代表这个文档application.xml
			Document doc = read.read(BeanFactory.class.getClassLoader().getResourceAsStream("application.xml"));
			//通过doc获取到根节点
			Element root=doc.getRootElement();
			//获取根节点下所有的子节点
			List<Element> list=root.elements();
			//遍历根节点下所有的子节点
			for(Element ele:list){
				//获取到根节点下属性名为id值
				String id=ele.attributeValue("id");
				if(name.equals(id)){
					//获取到当前节点下的class属性值
					String str=ele.attributeValue("class");
					//将str中存放的全路径代表的字节码文件加载到内存
					Class clazz=Class.forName(str);
					return clazz.getDeclaredConstructor().newInstance();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//	public static void main(String[] args) {
//		try {
//			CategoryDao c=(CategoryDao) BeanFactory.createObject("CategoryDao");
//			List<Category> allCats = c.findAllCats();
//			for(Category cc:allCats){
//				System.out.println(cc);
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
}
