package util.id;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.gline.controller.ALL.FightController;

/**
 * ID列表
 * @author 
 * 用于场景内部的实例ID.  
 * 
 * ID只有两种,一种是玩家ID这种全局ID, 一种是场景内的临时ID.
 * @create 2014-10-24
 */
public class IDList
{
	private static final Logger log = Logger.getLogger(IDList.class);
	private final BitSet idSet;
	
	private final ReentrantLock lock = new ReentrantLock();

	private int fromIndex = 1;
	
	private final int maxId;// = Integer.MAX_VALUE;
	
	private final int initId; //= 0;
	
	//回收ID
	
	
	public IDList(int maxId, int initId)
	{
		this.maxId = maxId;
		idSet = new BitSet(maxId); //默认false
		this.initId = initId;
	}
	
	
	/**
	 * 获得下一个ID
	 * @return
	 */
	public int nextId()
	{
		lock.lock();
		try 
		{
			int id;
			if(fromIndex == maxId)
			{
				throw new IDExceedMaximumException("ID已经用完,及时处理。");
			}
			else
			{
				id = idSet.nextClearBit(fromIndex);
			}
			
			if(id == maxId)
			{
				throw new IDExceedMaximumException("ID已经用完,及时处理。");
			}		

			if(idSet.get(id))
			{
				throw new DuplicateIDException("ID出现重复，请修正！");
			}
			idSet.set(id);	//设为true
			
			fromIndex = id + 1;
			
			return initId + id;
		} 
		finally
		{
			lock.unlock();
		}
	}
	
	
	
	/**
	 * 锁定ID
	 */
	public void LockIds(int... ids)
	{
		lock.lock();
		try
		{
			for(int id : ids)
			{
				if(idSet.get(id))
				{
					throw new DuplicateIDException("ID出现重复，请修正！");
				}
				idSet.set(id);
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	
	/**
	 * 锁定ID
	 * @param ids
	 */
	public void LockIds(List<Integer> ids)
	{
		lock.lock();
		try
		{
			for(int id : ids)
			{
				if(idSet.get(id))
				{
					throw new DuplicateIDException("ID出现重复，请修正！");
				}
				idSet.set(id);
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	
	
	
	/**
	 * 回收ID
	 */
	public void recoverId(int id)
	{
		lock.lock();
		try
		{
			if(!idSet.get(id))
			{
				/*TODO:抛出异常或输出日志*/
				return;
			}
			log.info("回收ID成功");
			idSet.clear(id);
			
			if(fromIndex > id || fromIndex == maxId)
				fromIndex = id;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	
	/**
	 * 回收ID
	 * @param ids
	 */
	public void recoverId(int... ids)
	{
		lock.lock();
		try
		{
			for(int id : ids)
			{
				if(!idSet.get(id))
				{
					/*TODO:抛出异常或输出日志*/
				}
				log.info("回收ID成功");
				idSet.clear(id);
				
				if(fromIndex > id || fromIndex == maxId)
					fromIndex = id;
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	
	/**
	 * 回收ID
	 * @param ids
	 */
	public void recoverId(List<Integer> ids)
	{
		lock.lock();
		try
		{
			for(int id : ids)
			{
				if(!idSet.get(id))
				{
					/*TODO:抛出异常或输出日志*/
				}
				log.info("回收ID成功");
				idSet.clear(id);
				
				if(fromIndex > id || fromIndex == maxId)
					fromIndex = id;
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	
	/**
	 * 获得已经使用的数量
	 */
	public int getUseCount()
	{
		return idSet.cardinality();
	}
	
}
