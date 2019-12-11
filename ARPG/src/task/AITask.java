package task;

import java.util.Map.Entry;

import com.gline.GameServerInit;
import com.gline.ai.AI;
import com.gline.ai.NpcStore;
import com.gline.module.npc.entity.NpcBaseEntity;

import thread.ExecuteThreadEver;
import thread.ThreadPoolManager;
import util.id.IDList;

/**
 * ѭ���߳�����ģ��
 * @author 	������
 * @date	2012-12-11
 */
public class AITask extends ExecuteThreadEver{

	@Override
	public void runTurn()
	{		
		for (Entry<Integer, NpcBaseEntity> entry : NpcStore.npcMap.entrySet())
		{
			AI.getInstance().Update(entry.getValue());
		}		
	}
	
	@Override
	public void sleepTurn() throws InterruptedException
	{		
		Thread.sleep(100);
	}
	
	@Override
	public void onFinally()
	{
		System.out.print("=========================AI线程终止！！！");
	}
	
	public void init()
	{
		ThreadPoolManager.getInstance().getCachepool().submit(new Thread(this));
	}

	@Override
	protected void begin() {
		// TODO Auto-generated method stub
		
	}
}
