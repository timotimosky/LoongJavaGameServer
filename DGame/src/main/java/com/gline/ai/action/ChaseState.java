package com.gline.ai.action;


import com.gline.ai.AI;
import com.gline.ai.SupAI;
import com.gline.ai._FSMState;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.person.PlayerEntity;

//有目的的寻路
public class ChaseState implements _FSMState
{
	NpcBaseEntity nNpc;
	
	public ChaseState(NpcBaseEntity mNpcBase)
	{
		
		nNpc = mNpcBase;
	}
	
	public void Reason ()
	{
		if(nNpc.state != 1)
		{
			return;
		}
		
		//如果当前目标还在追击范围内，继续追击
		
		//否则，丢失当前目标，并且
		
		
		if(System.nanoTime() - AI.getInstance().time >1000)
		{
			nNpc.state = 0;		
			AI.getInstance().time = System.nanoTime();
		}
	}

    /// <summary>
    /// 发送攻击请求
    /// </summary>
	public  void Act()
	{
        

	}

	/**
	 * @param scope ��������
	 */
	@SuppressWarnings({ "static-access", "unused" })
	public void action(SupAI ai, PlayerEntity playEntity, int harm) 
	{
		
		/*ScenceCode currMap = ScenceCode.getMapById(playEntity.getCurrMapID());
		
		if(playEntity == null) return;
		
		int x = playEntity.getTargetx();
		int y = playEntity.getTargety();
		
		List<Point> path = new PathSearch().findPath(currMap.getGrids(), ai.getNpcEntity().getLastPoint(), 
				playEntity.getLastPoint(),0);
		
		if(path ==null)
		{
			log.error("�޷��ҵ��������˵�·����");
			
			//�ظ�npc����ֵΪ��ʼֵ
			ai.setFightStatus(false);
			ai.getNpcEntity().setHp(ai.getNpcEntity().getInitHp());
			ai.getNpcEntity().setMp(ai.getNpcEntity().getInitMp());
			//ɾ��BUFF
			return;
		}
		
		else
		{
			ai.setRoleLocation(new int[]{x,y});//��¼��ұ������
			ai.setChangeMove(true);
			
		}*/
	}
}
