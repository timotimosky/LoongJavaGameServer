package com.gline.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.gline.module.npc.entity.NpcBaseEntity;
import com.gline.module.person.PlayerEntity;

import netBase.World;

/**
 * AI
 * @date 2013.4.10
 */
public abstract class SupAI
{
	private int aId;//
	
	private NpcBaseEntity npcEntity;//
	
	private _FSMState attack;//
	
	private _FSMState die ;//
	
	private _FSMState findTarger;//
	
	private _FSMState guard;//
	
	private _FSMState hatred;//
	
	private _FSMState hit;//
	
	private _FSMState move;//
	
	private _FSMState targetDie;//
	
	private _FSMState stop; //
	
	private float multiple;//
	
	//仇恨列表
	private ConcurrentHashMap<Integer,Integer> hatredList = new ConcurrentHashMap<Integer,Integer>();
	
	private long moveTime;//
	
	private long startStopMove;//
	
	private int roleId = 0;//
	
	private int[] roleLocation = new int[2];//
	
	private List<Integer> byAttackList = new ArrayList<Integer>();//

	private long attackTime;//
	
	private boolean fightStatus = false;//״̬
	
	private boolean changeMove = false;//
	
	private long reviveTime = 0;//
	
	/**
	 * ��ʼ��
	 * @param die
	 * @param move
	 * @param guard
	 * @param hit
	 */
	public SupAI(_FSMState die,_FSMState move,_FSMState guard,_FSMState hit,_FSMState attack,
			_FSMState hatred,_FSMState findTarger,_FSMState targetDie,_FSMState stop)
	{
		this.die = die;
		this.move = move;
		this.guard = guard;
		this.hit = hit;
		this.attack = attack;
		this.hatred = hatred;
		this.findTarger = findTarger;
		this.targetDie =targetDie;
		this.stop = stop;
	}
	
	/**
	 * AI��ʼ������������ʵ��
	 */
	public abstract void init();
	
	/***********************************************AI����Ϊ*****************************************/
	
	/**
	 * ������Ϊ
	 */
	public void attack(PlayerEntity roleEntity)
	{
		this.attack.Act();
	}
	
	/**
	 * ������Ϊ(��ͬ�Ĺ����в�ͬ��������ʽ)
	 */
	private void die()
	{
		this.die.Act();
	}
	
	/**
	 * Ѱ��Ŀ����Ϊ
	 */
	private void findTarger(PlayerEntity roleEntity)
	{
		this.findTarger.Act();
	}
	
	/**
	 * ������Ϊ
	 */
	private void guard()
	{
		this.guard.Act();
	}

	/**
	 * �����Ϊ
	 */
	private void hatred(PlayerEntity roleEntity, int harm)
	{
		this.hatred.Act();
	}
	
	/**
	 * ��������Ϊ
	 */
	private void hit(int harm,PlayerEntity roleEntity)
	{
		this.hit.Act();
	}
	
	/**
	 * �ƶ���Ϊ
	 */
	private void move()
	{
		this.move.Act();
	}
	
	/**
	 * Ŀ������
	 */
	private void targerDie(PlayerEntity roleEntity)
	{
		this.targetDie.Act();
	}
	
	/**
	 * ֹͣ����
	 */
	private void stop()
	{
		this.stop.Act();
	}
	/****************************************************AI��Ϊend************************************/
	/**
	 * �������,���ع����Ƿ�����
	 * @param roleEntity ���ʵ��
	 * @param harm  �����������Ϣ(��Ϊ����������AI����)
	 */
	public int isHit(PlayerEntity roleEntity,int harm)
	{
		int roleId = this.roleId;
		
		if(!this.fightStatus)//��ս��״̬�� ����Ӧ�����жϹ���Ŀ���Ƿ����ҵ�
		{
			this.findTarger(roleEntity);
		}
		
		this.hatred(roleEntity,harm);//�����ͻ������
		
		if(this.roleId != 0 && roleId == 0)//�����ս��״̬��ֹͣ��ǰ����
		{
			this.stop();
		}
		
		this.hit(harm,roleEntity);//�ܵ��˺�
		
		this.die();//����Ƿ�����
		
		return npcEntity.RoleState;
	}
	
	/**
	 * 攻击
	 */
	public void action()
	{
		PlayerEntity roleEntity = null;
		for(int i = 0 ; i < this.byAttackList.size() ; i++)
		{
			this.hit(i, roleEntity);
			this.hatred(roleEntity, i);
		}
		if(this.fightStatus)
		{
			//��ϵͳ������ȡ����ҽ�ɫ
			roleEntity = World.newInstance().getChannel(roleId).getPlayer();
			
			if(roleEntity == null)
			{
				
			}
			this.findTarger(roleEntity);
			this.move();
			this.attack(roleEntity);
			this.targerDie(roleEntity);//��ʱ������
		}
		else
		{
			this.guard();
			this.move();
		}
	}


	/********************************************GET/SET***********************************/
	
	public ConcurrentHashMap<Integer, Integer> getHatredList() 
	{
		return hatredList;
	}
	
	public int getRoleId() 
	{
		return roleId;
	}

	public void setRole(int role) 
	{
		this.roleId = role;
	}

	public NpcBaseEntity getNpcEntity() 
	{
		return npcEntity;
	}

	public void setNpcEntity(NpcBaseEntity npcEntity) 
	{
		this.npcEntity = npcEntity;
	}

	public float getMultiple() 
	{
		return multiple;
	}

	public void setMultiple(float multiple) 
	{
		this.multiple = multiple;
	}

	public long getAttackTime() 
	{
		return attackTime;
	}

	public void setAttackTime(long attackTime) 
	{
		this.attackTime = attackTime;
	}

	public boolean isFightStatus() 
	{
		return fightStatus;
	}

	public void setFightStatus(boolean fightStatus) 
	{
		this.fightStatus = fightStatus;
	}
	public long getReviveTime() 
	{
		return reviveTime;
	}
	public void setReviveTime(long reviveTime) 
	{
		this.reviveTime = reviveTime;
	}
	
	public long getMoveTime() {
		return moveTime;
	}
	public void setMoveTime(long moveTime) 
	{
		this.moveTime = moveTime;
	}
	public long getStartStopMove() 
	{
		return startStopMove;
	}
	public void setStartStopMove(long startStopMove) 
	{
		this.startStopMove = startStopMove;
	}
	public _FSMState getAttack() 
	{
		return attack;
	}
	public void setAttack(_FSMState attack) 
	{
		this.attack = attack;
	}
	public _FSMState getDie() {
		return die;
	}
	public void setDie(_FSMState die) 
	{
		this.die = die;
	}
	public _FSMState getFindTarger() 
	{
		return findTarger;
	}
	public void setFindTarger(_FSMState findTarger) 
	{
		this.findTarger = findTarger;
	}
	public _FSMState getGuard() 
	{
		return guard;
	}
	public void setGuard(_FSMState guard) 
	{
		this.guard = guard;
	}
	public _FSMState getHatred() 
	{
		return hatred;
	}
	public void setHatred(_FSMState hatred) 
	{
		this.hatred = hatred;
	}
	public _FSMState getHit() 
	{
		return hit;
	}
	public void setHit(_FSMState hit) 
	{
		this.hit = hit;
	}
	public _FSMState getMove() 
	{
		return move;
	}
	
	public void setMove(_FSMState move) 
	{
		this.move = move;
	}
	
	public _FSMState getTargetDie() 
	{
		return targetDie;
	}
	
	public void setTargetDie(_FSMState targetDie) 
	{
		this.targetDie = targetDie;
	}
	
	public _FSMState getStop() 
	{
		return stop;
	}
	
	public void setStop(_FSMState stop) 
	{
		this.stop = stop;
	}
	
	public void setHatredList(ConcurrentHashMap<Integer, Integer> hatredList) 
	{
		this.hatredList = hatredList;
	}
	
	public int[] getRoleLocation() 
	{
		return roleLocation;
	}
	
	public void setRoleLocation(int[] roleLocation) 
	{
		this.roleLocation = roleLocation;
	}
	
	public boolean isChangeMove() 
	{
		return changeMove;
	}
	
	public void setChangeMove(boolean changeMove) 
	{
		this.changeMove = changeMove;
	}
	public List<Integer> getByAttackList() 
	{
		return byAttackList;
	}
	public void setByAttackList(List<Integer> byAttackList) 
	{
		this.byAttackList = byAttackList;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}
	
}
