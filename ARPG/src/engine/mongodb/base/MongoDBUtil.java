package engine.mongodb.base;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.gline.GameServerInit;
import com.gline.Main;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
 
 
//数据库工具类
public class MongoDBUtil {
	public static Logger log = Logger.getLogger(MongoDBUtil.class);
    private static Mongo mongo = null;
    private DB db;
    
    private static String DBString = "Fish";//数据库名
    private static String hostName = "localhost";//主机名
    private static int port = 27017;//端口号
    private static int poolSize = 10;//连接池大小
     
    private MongoDBUtil(){}
	private final static MongoDBUtil gameInit = new MongoDBUtil();
	public  static MongoDBUtil getInstance(){
		return  gameInit;
	}
    
    
    //获取数据库连接
    public DB getDB(){
        if(mongo == null){
            init();
        }
        return db;
    }
     
     
    //初始化数据库
    private void init()
    {
        try 
        {
            //实例化Mongo
            mongo = new Mongo(hostName, port);
            db = mongo.getDB(DBString);
            
            if(db ==null)
            {
            	log.error("数据库不存在---"+DBString);
            	
            }
            
            //MongoOptions opt = mongo.getMongoOptions();
            //设置连接池大小
            //opt.connectionsPerHost = poolSize;
        } 
        catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void destory() {
        if (mongo != null)
        	mongo.close();
        mongo = null;
        db = null;
        //users = null;
        System.gc();
    }
}