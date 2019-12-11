package com.gline.ai.action;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.gline.ai.SupAI;
import com.gline.ai._FSMState;
import com.gline.module.person.PlayerEntity;

import netBase.World;

/**
 * AIĿ��������Ϊ
 */
public class NormalTargerDie implements _FSMState 
{
		
	public void Reason ()
	{

	}

    /// <summary>
    /// 发送攻击请求
    /// </summary>
	public  void Act()
	{
        

	}	
	
	
	public void action(SupAI ai, PlayerEntity roleEntity, int harm) {
		ConcurrentHashMap<Integer, Integer> hashMap = ai.getHatredList();
		
		PlayerEntity playEntity = null;
		
		for(Entry<Integer, Integer> e : hashMap.entrySet())//����������ˣ����Ҵӳ���б������
		{
			playEntity = World.newInstance().getChannel(e.getKey()).getPlayer();
			if(playEntity == null)
			{
				hashMap.remove(e.getKey());
				continue;
			}
			/*if(playEntity.getLiveStatus() == ParameterKey.STATUS_DIE && roleEntity.getRoleId() == playEntity.getRoleId())
			{
				ai.setRole(0);
			}
			if(playEntity.getLiveStatus() == ParameterKey.STATUS_DIE)
			{
				hashMap.remove(e.getKey());
			}*/
		}
		if(ai.getRoleId() == 0 && hashMap.size() == 0)//Ŀ��ȫ����(�ָ�BOSSѪ����ɾ��BOSS����BUFF)
		{
			//ai.getStop().action(ai,null,0);
			ai.setFightStatus(false);
			//ai.getNpcEntity().setTargetx(ai.getNpcEntity().getInitX());
			//֪ͨǰ̨
			//---д����ǰ̨�ƶ�������
			ai.setMoveTime(System.currentTimeMillis()/1000);
			return;
		}
		if(ai.getRoleId() == 0)//������Ŀ��
		{
			int roleId = 0;
			
			int hatred = 0;
			
			for(Entry<Integer, Integer> e : hashMap.entrySet())
			{
				if(e.getValue() > hatred)
				{
					hatred = e.getValue();
					roleId = e.getKey();
				}
			}
			ai.setRole(roleId);
		}
		
	}

}
