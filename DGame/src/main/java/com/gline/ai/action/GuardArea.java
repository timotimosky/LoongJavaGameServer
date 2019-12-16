package com.gline.ai.action;


import com.gline.ai.SupAI;
import com.gline.ai._FSMState;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.person.PlayerEntity;
import com.gline.module.scene.ScenceEntity;

/// <summary>
/// 警戒
/// </summary>
public class GuardArea implements _FSMState
{
	
	NpcBaseEntity nNpc;
	
	public GuardArea(NpcBaseEntity mNpcBase)
	{
		
		nNpc = mNpcBase;
	}
	
	public void Reason ()
	{

		if(nNpc.state != 5)
		{
			return;
		}
	}

    /// <summary>
    /// 发送攻击请求
    /// </summary>
	public  void Act()
	{
        

	}
	
	
	
	
	
	
	
	
	
	
	
	private ScenceEntity sceneEntity = null;

	public void action(SupAI ai, PlayerEntity playEntity, int harm) 
	{
		/*sceneEntity = NpcStore.sceneMap.get(ai.getNpcEntity().getCurrMapID());
		
		if(sceneEntity == null)return ;
		//�õ�NPC����ߵľ��䷶Χ
		int minx = ai.getNpcEntity().getTargetx() - ai.getNpcEntity().getGuardRange() > 0 ? ai.getNpcEntity().getTargetx() - ai.getNpcEntity().getGuardRange() : 0;
		//�õ�NPC���ұߵľ��䷶Χ
		int maxx = ai.getNpcEntity().getTargetx() + ai.getNpcEntity().getGuardRange() > ai.getMapAnalyzer().getMapWidth() ? ai.getMapAnalyzer().getMapWidth() : ai.getNpcEntity().getTargetx() + ai.getNpcEntity().getGuardRange();
		//�����NPC ���С���ƶ���Χ
		int[] xx = ai.getMapAnalyzer().patrolMove(ai.getNpcEntity().getTargetx(), ai.getNpcEntity().getTargety());
		//�жϾ��䷶Χ�ڵ�·���ǲ��Ƕ���ͬ��
		minx = minx > xx[0] ? xx[0] : minx;
		
		maxx = maxx > xx[1] ? xx[1] : maxx;
		
		PlayerEntity roleEntity = null;
		//ѭ����ͼ���е���
		for(int i = 0 ; i < sceneEntity.getRoles().size() ; i ++)
		{
			roleEntity = World.newInstance().getChannel(sceneEntity.getRoles().get(i)).getPlayer();
			
			if(roleEntity == null) continue;
			
			//�ж��Ƿ񹥻�
			if(roleEntity.getTargetx() > minx && roleEntity.getTargetx() < maxx)
			{
				ai.setRole(roleEntity.getRoleId());
				
				ai.setFightStatus(true);
				
				break;
			}
		}*/
	}

}
