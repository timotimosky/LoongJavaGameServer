package com.gline.module.fish;

//鱼的种类，死亡概率
public class FishType {

	//种类编号	名称	外观	个人收益组	是否可以瞄准	自动开炮目标优先级	金币掉落赔率	标准概率		实际概率	是否攻击加成	夺宝卡掉落概率	生活水深层次	图标	移动动画	死亡动画	碰撞框	描点	捕获后是否发送跑马灯	死亡特效	备注
	public String type	;
	public String name	;		
	public int benefitGroup	;
	public int aim	;
	public int autoFirePrority	;
	public int goldDrop	;//金币掉落赔率
	public int catchRate	; //死亡概率
	public int isAdd	;
	public int treasureDrop	;//夺宝卡掉落概率
	public int zorder	;
	public String icon	;
	public int moveAnim	;
	public int dieAnim	;
	public int deadEffect;
}
