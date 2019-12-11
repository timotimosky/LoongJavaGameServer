package com.gline.ai;

import util.RandomUtil;

/**
 * AI实际用到的实体
 * @date 2013.4.10
 */
public class SubAi extends SupAI
{
	
	public SubAi(_FSMState die, _FSMState move, _FSMState guard,
			_FSMState hit, _FSMState attack, _FSMState hatred,
			_FSMState findTarger, _FSMState targetDie, _FSMState stop) 
	{
		super(die, move, guard, hit, attack, hatred, findTarger, targetDie, stop);
	}

	public void init() 
	{
		
		//this.getNpcEntity().setLiveStatus(ParameterKey.STATUS_LIVE);
		
/*		this.getNpcEntity().setLastx(this.getNpcEntity().getInitX());
		this.getNpcEntity().setLasty(this.getNpcEntity().getInitY());
		this.getNpcEntity().setTargetx(this.getNpcEntity().getInitX());
		this.getNpcEntity().setTargety(this.getNpcEntity().getInitY());*/
		
		int level = this.getNpcEntity().getMaxLevel() - this.getNpcEntity().getMinLevel();
		
		if(level > 0)
		{
			level = RandomUtil.IntFromZero(level);
		}
		level = level + this.getNpcEntity().getMinLevel();
		
		///this.getNpcEntity().setLevel(level);
		
		//��AI���������
		NpcStore.aiMap.put(this.getaId(), this);
	}


}
