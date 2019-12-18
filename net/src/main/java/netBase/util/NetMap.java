package netBase.util;


import java.util.concurrent.ConcurrentHashMap;

import netBase.AloneNetMap;
import netBase.packet.ReceivablePacket;

/**
 *
 *<Integer,NetLink> --> key(Id),value()
 */
public class NetMap extends ConcurrentHashMap<Session,NetLink>
{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 创建映射，玩家ID对应玩家消息队列
	 * @param playerId
	 */
	public void careteNewLinek(Session gameClient)
	{
		
		if(null ==  AloneNetMap.netMap.get(gameClient))
		{
			this.put(gameClient, new NetLink());
		}
		else
		{
			this.remove(gameClient);
			this.put(gameClient, new NetLink());
		}
	}

	
	public void removeNewLinek(Session gameClient)
	{
		if(AloneNetMap.netMap.get(gameClient).size() <= 0)
		{
			AloneNetMap.netMap.remove(gameClient);
		}
	}
	
	/**
	 * 添加包到消息队列里
	 * @param playerId
	 * @param pack
	 */
	public void addPack(Session playerGameClient, ReceivablePacket pack) throws CloneNotSupportedException
	{
		if(null == AloneNetMap.netMap.get(playerGameClient))
		{
			AloneNetMap.netMap.put(playerGameClient, new NetLink());
		}
		
		AloneNetMap.netMap.get(playerGameClient).add(pack);		
	}
	
	
	
	/**
	 * 设置特定玩家的消息队列阻塞
	 */
	public void setBlockProcess(Session clinetId)
	{
		NetLink link = this.get(clinetId);
		
		if(null != link)
		{
			link.setProcess(false);
			
			//log.infoln("设置特定玩家的消息队列阻塞");
		}				
	}	
	
}

