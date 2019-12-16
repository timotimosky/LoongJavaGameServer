package task;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.gline.GameManager;
import com.gline.GameServerInit;
import com.gline.Excel.ReadExcelFishGruop;
import com.gline.Excel.ReadExcelFishRefresh;
import com.gline.ai.AI;
import com.gline.module.fish.FishRefresh;
import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.scene.ScenceEntity;

import thread.ExecuteThreadEver;
import thread.ThreadPoolManager;
import util.id.IDList;

/**
 * ѭ���߳�����ģ��
 * @author 	������
 * @date	2012-12-11
 */
public class SceneTask extends ExecuteThreadEver
{
	private static final Logger log = Logger.getLogger(SceneTask.class);
	//long LastUpdateTime=0;
	
	
	
	@Override
	public void runTurn()
	{		
		if (GameManager.ScenceEntityMap.size()!=0) {
			for(ScenceEntity mScenceEntity : GameManager.ScenceEntityMap.values())
			{
				//log.info("当前总场景"+GameManager.ScenceEntityMap.size()+"  当前遍历场景id= "+mScenceEntity.Entityid);
				mScenceEntity.update();
			}
		}
		if (GameManager.BigScenceMap.size()!=0) {
			for(ScenceEntity mScenceEntity : GameManager.BigScenceMap.values())
			{
				mScenceEntity.update();
			}
		}
		if (GameManager.VipScenceMap.size()!=0) {
			for(ScenceEntity mScenceEntity : GameManager.VipScenceMap.values())
			{
				mScenceEntity.update();
			}
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
