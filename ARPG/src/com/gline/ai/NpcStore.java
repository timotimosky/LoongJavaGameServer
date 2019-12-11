package com.gline.ai;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.gline.ai.entity.NpcBornEntity;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.scene.ScenceEntity;

/**
 * NPC
 */
public class NpcStore 
{
	
	/**
	 * 场景所有实体
	 */
	public static ConcurrentHashMap<Integer, ScenceEntity> sceneMap = new ConcurrentHashMap<Integer, ScenceEntity>();
	
	/**
	 * NPCԭ��
	 */
	public static HashMap<Integer, NpcBaseEntity> npcMap = new HashMap<Integer, NpcBaseEntity>();
	/**
	 * NPCʵ�����
	 */
	public static ConcurrentHashMap<Integer, NpcBaseEntity> npcArray = new ConcurrentHashMap<Integer, NpcBaseEntity>();
	
	/**
	 * NPCˢ�µ�ԭ��
	 */
	public static final HashMap<Integer,  List<NpcBornEntity> > npcBornMap = new HashMap<Integer,  List<NpcBornEntity> >();
	
	/**
	 * AI����
	 */
	public static ConcurrentHashMap<Integer, SupAI> aiMap = new ConcurrentHashMap<Integer, SupAI>();
	
	/**
	 * Action����
	 */
	public static final HashMap<String, _FSMState> actionMap = new HashMap<String, _FSMState>();
	
	/**
	 * NPC��ˮID
	 */
	public static int NPC_SYSTEM_ID;
	
	/**
	 * AI��ˮID
	 */
	public static int AI_SYSTEM_ID;
	
	
	//获取NPC的ID
	public synchronized static int getSystemIndexId()
	{
		if(NPC_SYSTEM_ID>=Integer.MAX_VALUE)
		{
			NPC_SYSTEM_ID=0;
		}
		return ++NPC_SYSTEM_ID;
	}
	
	public synchronized static int getAiSystemId()
	{
		if(AI_SYSTEM_ID>=Integer.MAX_VALUE)
		{
			AI_SYSTEM_ID=0;
		}
		return ++AI_SYSTEM_ID;
	}
}
