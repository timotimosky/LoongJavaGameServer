package com.gline.ai.entity;

import java.io.Serializable;

/**
 * NPC�����Ǯ
 *
 */
public class NpcDropMoneyEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int moneyType;//��������
	
	private int minMoney;//��С��������
	
	private int maxMoney;//����������

	public int getMoneyType() 
	{
		return moneyType;
	}

	public void setMoneyType(int moneyType) 
	{
		this.moneyType = moneyType;
	}

	public int getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(int minMoney) 
	{
		this.minMoney = minMoney;
	}

	public int getMaxMoney() 
	{
		return maxMoney;
	}

	public void setMaxMoney(int maxMoney) 
	{
		this.maxMoney = maxMoney;
	}
	
	

}
