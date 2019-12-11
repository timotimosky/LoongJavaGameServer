package com.gline.ai.action;


import com.gline.ai._FSMState;
import com.gline.module.npc.entity.NpcBaseEntity;


public class DeadState implements _FSMState
{
	NpcBaseEntity nNpc;
	
	public DeadState(NpcBaseEntity mNpcBase)
	{
		
		nNpc = mNpcBase;
	}
	
	 	//public delegate void DeadHandler(Transform tr);
	    //public event DeadHandler DeadEvent;


		//private GameObject my;
		//private GroupController control;

	   /* public DeadState(GameObject go)
	    {
			control = go.GetComponent<GroupController>();
			my = go;
	    }*/
		
	    float dieTime = 0;
	    boolean hasDied = false;

	    public void Reason()
	    {
	    	
	    } 

	    public void Act()
	    {

	        if (!hasDied)
	        {
	               /* Debug.Log("============ die ============ " + control.mEntity.VisualId);
					my.GetComponent<Collider>().enabled = false;

	                if (DeadEvent != null)
	                    DeadEvent(control.transform);
	            
	                control.GroupDie();
	                dieTime = Time.time;

	                hasDied = true;

	                GameObject.Find("GroupsManager").GetComponent<GroupsManager>().DeleteTroopDelay(my, 1f);*/

	        }
	    }
	
	
	
	/*private NpcBaseEntity npcEntity;
	private ScenceEntity sceneEntity;
	
	public void action(SupAI ai, PlayerEntity roleEntity, int harm) {
		npcEntity = ai.getNpcEntity();//�õ�npc
		
		if(npcEntity.getHp() < 0)//����С��0 ������
		{
			//npcEntity.setLiveStatus(ParameterKey.STATUS_DIE);
			
			//ˢ��ʱ��
			int revivetime = npcEntity.getTimeMin() + RandomUtil.IntFromZero(npcEntity.getTimeMax());
			
			ai.setReviveTime(System.currentTimeMillis() + (long)revivetime);
			
			if(npcEntity.getSelectedRoleList()!=null)npcEntity.getSelectedRoleList().clear();//���ǰѡ�д�NPC���������
			
			//��ȡ����
			sceneEntity = NpcStore.sceneMap.get(npcEntity.getCurrMapID());
			
			if(sceneEntity != null)
			{
				sceneEntity.getNpcs().remove(Integer.valueOf(npcEntity.getRoleId()));
			}
			//��������֪ͨǰ̨(��)
			
			
			//������(��)
			
		}
		
	}*/
}
