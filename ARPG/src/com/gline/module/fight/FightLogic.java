package com.gline.module.fight;


import org.apache.log4j.Logger;

import com.gline.module.enums.ErrorCodeType;
import com.gline.module.fish.FishGroup;
import com.gline.module.person.PlayerEntity;

/**
 * 战斗逻辑类
 * @author 
 * @date 2012-11-1
 */
public class FightLogic 
{
	private final static FightLogic fightLogic = new FightLogic();
	
	private Logger log = Logger.getLogger(FightLogic.class);
	
	private FightLogic(){}
	
	public static FightLogic newInstance()
	{
		return fightLogic;
	}
	
	
	/**
	 * 发射炮弹,击中鱼
	 * @param player				玩家实体
	 * @param id					目标ID
	 * @param type					目标类型
	 * @param skillId				技能ID
	 * @param x						X坐标
	 * @param y						Y坐标
	 * @param attackType			是否是强制攻击(参加EnforceAttackType枚举)
	 * @create
	 */
	public void castSkill(PlayerEntity player, long id, int hit, int skillId,  int attackType)
	{
		int state = ErrorCodeType.CODE_SUCCESS.getCode();
		
		//如果该鱼被其他人击杀了..return
		
		//TODO 找到该鱼
		FishGroup mFishEntity = new FishGroup();
		
		//TODO 获取该玩家目前使用的炮弹伤害值
		int harm = 10;
		
		//向另一玩家通知被攻击
		//FightMessage.GC_CAST_SKILL(player, state);
		/*StringBuilder buidler = new StringBuilder("玩家施放技能---玩家ID[").append(player.getId())
				.append("]施放技能的ID[").append(skillId).append("]施放的状态[").append(state).append("]");
		log.info(buidler.toString());*/
	}
	
	
	//战斗胜负
	
	
	
	
	/**
	 * 施放技能  (普通攻击也作为技能)
	 * @param player				玩家实体
	 * @param id					目标ID
	 * @param type					目标类型
	 * @param skillId				技能ID
	 * @param x						X坐标
	 * @param y						Y坐标
	 * @param attackType			是否是强制攻击(参加EnforceAttackType枚举)
	 * @create
	 */
	public void castSkill(PlayerEntity player, long id, int type, int skillId, int x, int y, int attackType)
	{
		int state = ErrorCodeType.CODE_SUCCESS.getCode();
		/*判断玩家是否死亡
		if(player.getState().isLive())
		{
			SkillEntity skill = player.gainSkEntity().get(skillId);
			//判断玩家是否有该技能
			if(skill == null) return;
			
			long time = System.currentTimeMillis();
			
			if(player.gainLastTime() + FightConfig.ATTACK_INTERVAL < time)
			{
				//调用施放技能
				state = FightService.castSkill(player, id, type, skill.gainEffect(), x, y, attackType);
				if(state == 0)
					player.putLastTime(time);
			}
			else
				state = ErrorCodeType.CODE_ERROR12.getCode();
		}
		else
		{
			//玩家已经死亡
			state = ErrorCodeType.CODE_ERROR14.getCode();
		}*/
		
		
		//向另一玩家通知被攻击
		//FightMessage.GC_CAST_SKILL(player, state);
		/*StringBuilder buidler = new StringBuilder("玩家施放技能---玩家ID[").append(player.getId())
				.append("]施放技能的ID[").append(skillId).append("]施放的状态[").append(state).append("]");
		log.info(buidler.toString());*/
	}
	
}
