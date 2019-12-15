package engine.mongodb;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

import engine.mongodb.base.BaseDAOImpl;
import engine.mongodb.base.MongoDBUtil;

public class test {
	private static final Logger log = Logger.getLogger(test.class);
	
	public static void testPut()
	{
        BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        BasicDBObject beanOne = new BasicDBObject();
        beanOne.put("name", "kakakaka");
        beanOne.put("sex", "男");
        beanOne.put("age", 20);
        baseDAOImpl.insert("test", beanOne);
        testSelect();
	}
	public static void testSelect()
	{
		DB db = MongoDBUtil.getInstance().getDB();
		
		Set colls = db.getCollectionNames();
		for (Object s : colls) {
		   log.info("数据库中所有集合为:"+s.toString());
		}
		
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        List<BasicDBObject> list = baseDAOImpl.find("test", new BasicDBObject("name", "kakakaka"));
        for(BasicDBObject i : list){
            log.info(i.get("name"));
            log.info(i.get("sex"));
            log.info(i.get("age"));
        }	
		testDelete();
        
	}
	
	public static void testUpdate()
	{
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
	     BasicDBObject oldBean = (BasicDBObject) baseDAOImpl.find("test", new BasicDBObject("name", "kakakaka")).get(0);
	        BasicDBObject newBean = (BasicDBObject) oldBean.clone();
	        newBean.put("name", "gugugugu");
	        log.info(oldBean.get("name"));
	        log.info(newBean.get("name"));
	        baseDAOImpl.update("test", oldBean, newBean);
	}
	
	public static void testDelete()
	{
        BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
/*        BasicDBObject beanOne = new BasicDBObject();
        beanOne.put("name", "gugugugu");
        beanOne.put("sex", "男");
        beanOne.put("age", 20);
        baseDAOImpl.insert("test", beanOne);*/
        
        /*baseDAOImpl.update("test", new BasicDBObject("_id", new ObjectId("5471c9db44aeeb8b5524f2ea"))
        , new BasicDBObject("name","kakakaka").append("age", 20));*/
        
        baseDAOImpl.delete("test", new BasicDBObject("name","kakakaka"));

	}
	
	
	
}

