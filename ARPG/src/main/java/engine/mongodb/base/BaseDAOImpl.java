package engine.mongodb.base;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
 
//数据库基本操作实现
public class BaseDAOImpl implements BaseDAO {
 
    @Override
    public boolean insert(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getInstance().getDB();
        db.getCollection(collectionName).insert(bean);
        return false;
    }
 
    @Override
    public boolean delete(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getInstance().getDB();
        db.getCollection(collectionName).remove(bean);
        return false;
    }
 
    @Override
    public List find(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getInstance().getDB();
        List list = db.getCollection(collectionName).find(bean).toArray();
        return list ;
    }
 
    public DBObject findOne(String collectionName, BasicDBObject bean) {
        DB db = MongoDBUtil.getInstance().getDB();
        return db.getCollection(collectionName).findOne(bean);
    }
    
    public DBObject findOne(String collectionName, BasicDBObject bean, BasicDBObject bean2) {
        DB db = MongoDBUtil.getInstance().getDB();
        return db.getCollection(collectionName).findOne(bean,bean2);
    }
    
    
    @Override
    public boolean update(String collectionName, BasicDBObject oldBean, BasicDBObject newBean) {
        DB db = MongoDBUtil.getInstance().getDB();
        db.getCollection(collectionName).update(oldBean, newBean);
        return false;
    }
 
}
