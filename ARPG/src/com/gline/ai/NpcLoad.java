package com.gline.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.gline.ai.entity.NpcBornEntity;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.scene.ScenceEntity;

import util.RandomUtil;

/**
 * �ڳ����д���NPC
 *
 */
public class NpcLoad 
{

	private Logger logger = Logger.getLogger(NpcLoad.class);
	/**
	 * ��ʼ������NPC
	 */
	public void sceneNpcLoad()
	{
				
		Map<Integer, ScenceEntity> sceneMap = NpcStore.sceneMap;//��������
		
		HashMap<Integer,  List<NpcBornEntity> > npcBornMap = NpcStore.npcBornMap;//NPC��������
		
		HashMap<Integer, NpcBaseEntity> npcMap = NpcStore.npcMap;//NPC����

		List<NpcBornEntity>  npcBornList = null;//��ͼˢ�µ�LIST
		
		ScenceEntity sceneEntity = null;//����ʵ��
		
		for(Entry<Integer, ScenceEntity> e : sceneMap.entrySet())
		{
			sceneEntity = e.getValue();
			
			//if(sceneEntity.getMapType() == 2)//2Ϊ������ͼ,������ͼ�ڳ�ʼ����ʱ���ü������
			//{
				//continue;
			//}
			
			npcBornList = npcBornMap.get(e.getKey());
		
			
			if(npcBornList != null)
			{
				this.addNpcScene(sceneEntity, npcBornList, npcMap);//��NPC���볡��
			}
		}
	}
	
	
	/**
	 * �������NPC
	 * 
	 * ��npc�������ɶ�Ӧ��npc
	 */
	public void addNpcScene(ScenceEntity sceneEntity, List<NpcBornEntity> npcBornList,HashMap<Integer, NpcBaseEntity> npcMap)
	{
		NpcBornEntity npcBornEntity = null;//NPC�����ʵ��
		NpcBaseEntity npcBaseEntity = null;//NPCʵ��
		NpcBaseEntity npcBaseCloneEntity = null;//��¡�Ժ��NPCʵ��
		
		//Map<Long,RoleAbstracts> npcs =  sceneEntity.EnityMap;
			
		{
			for(int i = 0 ; i < npcBornList.size() ; i++)
			{
				npcBornEntity = npcBornList.get(i);
				
				if(npcBornEntity == null)
				{
					continue;
				}
				
				npcBaseEntity = npcMap.get(npcBornEntity.getNpcId());
				
				if(npcBaseEntity == null)
				{
					logger.info("����ϵͳ��û��NPC:"+npcBornEntity.getNpcId());
					continue;
				}
				
				//��ȡnpc��Ѳ�߷�Χ��������Сx���;
				for(int j = 0 ; j < npcBornEntity.getNumber() ; j++)
				{
					npcBaseCloneEntity = npcBaseEntity.clone();
					
					//��npc��Ѳ�߷�Χ�ڣ������npc��������
					int x = npcBornEntity.getX() + RandomUtil.IntFromZero(npcBaseEntity.getPatrolRange());
					int y = npcBornEntity.getY() + RandomUtil.IntFromZero(npcBaseEntity.getPatrolRange());
					
/*					//��NPC���븳����Ϊ
					this.aiInit(npcBaseCloneEntity,sceneEntity.getId(), x, y);
					
					npcs.add(npcBaseCloneEntity.getRoleId(),npcBaseCloneEntity);
					
					NpcStore.npcArray.put(npcBaseCloneEntity.getRoleId(), npcBaseCloneEntity);*/
				}
			}
		}
	}
	
	/**
	 * 初始化AI
	 */
	public void aiInit(NpcBaseEntity npcBaseCloneEntity,int mapId,int x,int y)
	{
		//npcBaseCloneEntity.setRoleId(NpcStore.getSystemIndexId());
		
		npcBaseCloneEntity.setInitX(x);
		
		npcBaseCloneEntity.setInitY(y);
		
		//npcBaseCloneEntity.setCurrMapID(mapId);
		
		npcBaseCloneEntity.setBaseId(NpcStore.getSystemIndexId());
		
		npcBaseCloneEntity.setNpcAiId(NpcStore.getAiSystemId());
		
	}
}
