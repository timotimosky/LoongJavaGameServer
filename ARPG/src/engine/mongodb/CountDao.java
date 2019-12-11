package engine.mongodb;

import org.apache.log4j.Logger;

import com.gline.module.count.CountEnity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import engine.mongodb.base.BaseDAOImpl;

public class CountDao {

	public static final String tableName = "CountEnity";
	private static final Logger log = Logger.getLogger(CountDao.class);
	public static CountEnity get(DBObject mDBObject)
	{
		CountEnity mCountEnity= new CountEnity();
		mCountEnity.count = mDBObject.get("count").toString();
		mCountEnity.password = mDBObject.get("password").toString();
		mCountEnity.mac = mDBObject.get("mac").toString();
		mCountEnity.playerPID = Long.parseLong(mDBObject.get("playerPID").toString());
		mCountEnity.phoneNumber = mDBObject.get("phoneNumber").toString();
		return mCountEnity;
	}
	
	
	
	public static void Put(CountEnity mCountEnity)
	{
        BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        BasicDBObject beanOne = new BasicDBObject();
        beanOne.put("count", mCountEnity.count);
        beanOne.put("password", mCountEnity.password);
        beanOne.put("mac", mCountEnity.mac);
        beanOne.put("playerPID", mCountEnity.playerPID);
        beanOne.put("phoneNumber", mCountEnity.phoneNumber);
        
        baseDAOImpl.insert(tableName, beanOne);
        
        //初始化账号时，注册账号的相关角色	
	}

	//单个属性查找
	public static CountEnity SelectCount(String propertyType,String propertyValue)
	{
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
		DBObject mDBObject = baseDAOImpl.findOne(tableName, new BasicDBObject(propertyType, propertyValue)); 
		if(mDBObject ==null )
			return null;
		if (mDBObject.get(propertyType) == null) {
			return null;
		}		
		
		return get(mDBObject);
	}
	
	public static void UpdateByPID(CountEnity mCountEnity)
	{
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
		BasicDBObject oldBean = new BasicDBObject("playerPID", mCountEnity.playerPID);	
		BasicDBObject newBean = new BasicDBObject();
		 
		newBean.put("count", mCountEnity.count);
	    newBean.put("password", mCountEnity.password);
	    newBean.put("mac", mCountEnity.mac);
	    newBean.put("playerPID", mCountEnity.playerPID);
	    newBean.put("phoneNumber", mCountEnity.phoneNumber);	      
	        
	    baseDAOImpl.update(tableName, oldBean, newBean);
        
	     log.info(oldBean.get("card"));
	     log.info(newBean.get("card"));
	     baseDAOImpl.update(tableName, oldBean, newBean);
	}
	
	public static void Delete(CountEnity mCountEnity)
	{
        BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        
        baseDAOImpl.delete(tableName, new BasicDBObject("playerPID",mCountEnity.playerPID));

	}
}
