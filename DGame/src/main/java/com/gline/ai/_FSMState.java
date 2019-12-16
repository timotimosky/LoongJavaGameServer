package com.gline.ai;

/// <summary>
/// 本状态机分为4部分
/// Reason为数据收集、状态分析
/// GroupController为核心类，提供Update和FixUpdate供使用，并且玩家操作也在这个类进行，
/// Act为状态执行
/// Area为数据收集类，通过触发器收集数据，但绝对不能直接处理状态。如果此处做状态控制，则会引发问题，当玩家操控的时候，Controller改变状态，引发冲突
/// </summary>
public interface _FSMState 
{
	//public void action(SupAI ai,PlayerEntity roleEntity,int harm,int scope);
	//public abstract void action(SupAI ai,PlayerEntity roleEntity,int harm);
	
	/// <summary>
	/// 数据收集，只收集该实体在该状态下的所有数据，不做处理，在Update执行
	/// 数据包括：服务端发送来的消息、以及通过碰撞器、射线、遍历等手段收集周围的消息
    /// 
	/// </summary>
	/// <param name="go">Go.</param>
    public abstract void Reason();

	/// <summary>
	/// 状态处理，只处理状态，既不收集数据，也不对数据做处理，在FixUpdate执行
	/// </summary>
	/// <param name="go">Go.</param>
    public abstract void Act();
}
