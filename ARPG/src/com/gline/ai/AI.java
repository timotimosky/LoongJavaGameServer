package com.gline.ai;

import org.apache.log4j.Logger;

import com.gline.module.npc.entity.NpcBaseEntity;

import util.id.IDList;


public class AI {

	private static final Logger log = Logger.getLogger(AI.class);
	
	IDList mIDList;
	
	
	private final static AI AIInit = new AI();
	private AI(){}
	public  static AI getInstance(){
		return  AIInit;
	}
	
	
	public  double getDistance(int x, int y,  int tx, int ty){
		double _x = Math.abs(x -tx);
		double _y = Math.abs(y - ty);
		return Math.sqrt(_x*_x+_y*_y);
	}
	
	//移动，5秒后返回state=0的状态
	public long time;
	
	public  void Update(NpcBaseEntity nNpc)
	{
		
		nNpc.mAttackState.Reason();
		nNpc.mChaseState.Reason();
		nNpc.mDeadState.Reason();
		nNpc.mGuardArea.Reason();
		//nNpc.mIdleState.Reason();
		
		//状态  0待机  1移动 2返回出生点 3攻击 4巡逻  5 追击
		
		//回城状态
		if(nNpc.state == 2)
		{			
			if(System.nanoTime() - time >3000)
			{
				nNpc.state = 0;		
				time = System.nanoTime();
			}
		}

	}
}

