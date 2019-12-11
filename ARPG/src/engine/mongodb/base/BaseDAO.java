package engine.mongodb.base;

import java.util.List;

import com.mongodb.BasicDBObject;
 
 
 
//数据库CRUD基本操作
public interface BaseDAO {
    public boolean insert(String collectionName, BasicDBObject bean);
     
    public boolean delete(String collectionName, BasicDBObject bean);
     
    public List find(String collectionName, BasicDBObject bean);
     
    public boolean update(String collectionName, BasicDBObject oldBean, BasicDBObject newBean);
     
     
}
