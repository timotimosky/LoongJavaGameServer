package engine.mybatis.dao.Interface;

import java.util.List;
import java.util.Map;

import com.gline.module.person.PlayerEntity;

public  interface PlayerDao {

	List<PlayerEntity> search(Map<String,Object> map);
	
	PlayerEntity get(int id);
	
	void save(PlayerEntity n);
	
	PlayerEntity getByName(String name);
	
	void delete(String p);
	
	void update(PlayerEntity v);
	
	void insert(PlayerEntity entity);
	
  
     
	

}
