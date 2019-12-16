package engine.mybatis.dao.Interface;

import com.gline.module.npc.entity.NpcEntity;



public interface NpcDao {
	

	//List<NpcEntity> search(Map<String,Object> map);
	
	NpcEntity get(int npc_id);
	
	void save(NpcEntity n);
	
	//void delete(String p);
	
	//void edit(NpcEntity v);
	
}
