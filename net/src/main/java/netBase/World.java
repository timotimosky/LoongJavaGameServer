package netBase;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.channel.ChannelId;
import netBase.util.Session;

//保存整个游戏服所有链接
public class World<T extends Session>
{
	//单例
	private World(){}
	private static final class singleton{
		private static final World<Session> world = new World<Session>();
	}
	public static World<Session> newInstance()
	{
		return  singleton.world;
	}
	
	//private static final Logger log = Logger.getLogger(PlayerLogic.class);

	/*ConcurrentHashMap*/
	protected static ConcurrentHashMap<ChannelId, Session> channelMap = new ConcurrentHashMap<ChannelId, Session>();
	
	
	/**
	 * 
	 * 
	 */
	public ConcurrentHashMap<ChannelId, Session> getChannelMap()
	{
		return channelMap;
	}
	
	/**
	 * 注册连接
	 * @param connection
	 */
	public void addChannel(Session connection)
	{
		
		Session older = channelMap.get(connection.getChannel().id());
		
		/**判断连接是否注册**/
		if(older != null)
		{
			StringBuilder builder = new StringBuilder("连接[").append(connection.getChannel().id())
					.append("]已经注册。");
			//log.warn(builder.toString());
			return ;
		}
		
		/**将连接加入到玩家连接队列中**/
		channelMap.put(connection.getChannel().id(), connection);
		
		StringBuilder builder = new StringBuilder("新连接建立，连接ID[").append(connection.getChannel().id());
		builder.append("]连接地址[")
		.append(connection.getChannel().remoteAddress().toString()).append("]");
	//	log.info(builder.toString());
	}
	
	
	
	/**
	 * 注销连接
	 * @param channelId		连接ID
	 */
	public void removeConnection(ChannelId channelId)
	{
		if(!channelMap.containsKey(channelId))
		{
			StringBuilder builder = new StringBuilder("连接[").append(channelId).append("]没有注册，所以无法注销。");
		//	log.warn(builder.toString());
			return ;
		}
		/**关闭任务管理和**/
		channelMap.remove(channelId);
		StringBuilder builder = new StringBuilder("连接[").append(channelId).append("]关闭连接。");

	//	log.info(builder.toString());
	}

	
	/**
	 * 获得世界中所有连接
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Session>Collection<T> getValues()
	{
		return (Collection<T>) channelMap.values();
	}
	
	public Session getChannel(ChannelId id)
	{
		return channelMap.get(id);
	}

}
