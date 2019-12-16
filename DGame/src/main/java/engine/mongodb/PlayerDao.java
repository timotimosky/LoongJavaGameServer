package engine.mongodb;

import java.io.ObjectStreamException;
import java.util.List;
import org.apache.log4j.Logger;
import com.gline.module.person.PlayerEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import engine.mongodb.base.BaseDAOImpl;

public class PlayerDao {
	private static final Logger log = Logger.getLogger(PlayerDao.class);
	public  transient static final String tableName = "PlayerEntity";
	
	@SuppressWarnings("unchecked")
	public static PlayerEntity getPlayer(DBObject mDBObject)
	{
		PlayerEntity mPlayerEntity= new PlayerEntity();
		mPlayerEntity.name = mDBObject.get("name").toString();
		mPlayerEntity.pid = Long.parseLong(mDBObject.get("pid").toString());
		mPlayerEntity.level = (Integer.parseInt(mDBObject.get("level").toString()));
		mPlayerEntity.card = (Integer.parseInt(mDBObject.get("card").toString()));
		mPlayerEntity.Rmb = (Integer.parseInt(mDBObject.get("diamond").toString()));
		mPlayerEntity.gold = (Integer.parseInt(mDBObject.get("gold").toString()));
		mPlayerEntity.allGold = (Integer.parseInt(mDBObject.get("allGold").toString()));
		mPlayerEntity.gunType = (Short.parseShort(mDBObject.get("gunType").toString()));
		
		//为了上线后也能增加字段 可以写为
/*		String gun = mDBObject.get("gunType").toString();
		if (gun == null) {
			mDBObject.put("gunType", 1);
		}*/
		
		Object gunlist = mDBObject.get("GunList");
		mPlayerEntity.GunList = (List<Integer>)(gunlist);
		for(Integer mInteger : mPlayerEntity.GunList)
		{
			log.info("GunList=="+mInteger);
		}
		
		return mPlayerEntity;
	}
	//创建新实体时,put
	public static void Put(PlayerEntity mPlayerEntity)
	{
        BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        BasicDBObject beanOne = new BasicDBObject();
        beanOne.put("name", mPlayerEntity.name);
        beanOne.put("pid", mPlayerEntity.pid);
        beanOne.put("level", mPlayerEntity.level);
        beanOne.put("card", mPlayerEntity.card);
        beanOne.put("diamond", mPlayerEntity.Rmb);
        beanOne.put("gold", mPlayerEntity.gold);
        beanOne.put("allGold", mPlayerEntity.allGold);
        beanOne.put("GunList", mPlayerEntity.GunList);
        beanOne.put("gunType", mPlayerEntity.gunType);
        baseDAOImpl.insert(tableName, beanOne);  
		
	}
	
	public static PlayerEntity SelectPlayer(String propertyType,String propertyValue)
	{
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
		DBObject mDBObject = baseDAOImpl.findOne(tableName, new BasicDBObject(propertyType, propertyValue)); 
		if(mDBObject ==null )
		{
			log.error("无法查询到该玩家 propertyType="+propertyType);
			return null;			
		}
		
		return getPlayer(mDBObject);
	}
	
	public static PlayerEntity SelectPlayerByPid(long pid)
	{
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
		DBObject mDBObject = baseDAOImpl.findOne(tableName, new BasicDBObject("pid", pid)); 
		if(mDBObject ==null )
			return null;
	
		return getPlayer(mDBObject);
	}
	
	public static DBObject SelectByName(String name)
	{
		BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
		DBObject i = baseDAOImpl.findOne(tableName, new BasicDBObject("name", name)); 	
        return i;
	}
	
	public static void Update(PlayerEntity mPlayerEntity)
	{		
		 BaseDAOImpl baseDAOImpl = new BaseDAOImpl();  	
         BasicDBObject oldBean = new BasicDBObject("pid", mPlayerEntity.pid);	
         
		 //不能有遗漏
         //比如 不put pid的话，则默认认为pid为空， 会用空值覆盖。  所以所有值都要写上。
		 BasicDBObject newBean = new BasicDBObject();
		 newBean.put("pid", mPlayerEntity.pid);
	     newBean.put("name", mPlayerEntity.name);
	     newBean.put("level", mPlayerEntity.level);
	     newBean.put("card", mPlayerEntity.card);
	     newBean.put("diamond", mPlayerEntity.Rmb);
	     newBean.put("gold", mPlayerEntity.gold);
	     newBean.put("allGold", mPlayerEntity.allGold);
	     newBean.put("GunList", mPlayerEntity.GunList);
	     newBean.put("gunType", mPlayerEntity.gunType);
	 	//玩家目前装备的炮塔Type

/*	   	 newBean.replace("name", mPlayerEntity.name);
	     newBean.replace("pid", mPlayerEntity.Pid);
	     newBean.replace("level", mPlayerEntity.level);
	     newBean.replace("card", mPlayerEntity.card);
	     newBean.replace("diamond", mPlayerEntity.diamond);
	     newBean.replace("gold", mPlayerEntity.gold);*/
	     //log.info(oldBean.get("card"));
	    // log.info(newBean.get("card"));
	     baseDAOImpl.update(tableName, oldBean, newBean);
	}
	
	public static void Delete(PlayerEntity mPlayerEntity)
	{
        BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
        
        baseDAOImpl.delete(tableName, new BasicDBObject("pid",mPlayerEntity.pid));

	}
}
