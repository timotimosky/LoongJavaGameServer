package netBase.netty;

import netBase.AloneNetMap;
import netBase.ReceivablePacket;
import netBase.World;
import netBase.util.GameClient;

/**
 *  分发库
 * @author timosky
 * @date 2013-1-8
 */  
public class ServerHandler extends SimpleChannelHandler {
	
	private static final Logger log = Logger.getLogger(ServerHandler.class);
	/**
	 * 接收消息
	 */
	@Override 
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception  {
	    
		
		if(!(e.getMessage() instanceof ReceivablePacket))
		{
			return;
		}
		
		ReceivablePacket packet = (ReceivablePacket) e.getMessage();
		
		if(packet.getBuffer().capacity()< 3)
		{
			return ;
		}
		
		Integer id = ctx.getChannel().getId();
		//添加映射
		packet.addClient(id);	
			
		//log.infoln( ctx.getChannel().getId() + "的通道  messageReceived");
		
		GameClient client = World.newInstance().getChannel(id);
		
		/*将消息包放入玩家对应的消息队列*/
		AloneNetMap.netMap.addPack(client,packet);
	
    }  
	

	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		GameClient client = new GameClient(ctx.getChannel());
		
		//将这个链接添加到world里，方便以后查询
		World.newInstance().addChannel(client);
		
		//添加映射：玩家client--玩家所拥有的消息队列	
		AloneNetMap.netMap.careteNewLinek(client);	
	}
	
	/**
	 * 异常
	 */
    @Override 
 	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) 
    {	 
    	//通信异常,就不能调用通知该链接关闭通信的 		SceneMessage.GC_SelfLeaveSence(client);了
    	//不然会导致写入socket出错
    	
    	log.error("通信异常,执行删除map操作,不然出错----"+e);
    	
		Integer id = ctx.getChannel().getId();
		GameClient client = World.newInstance().getChannel(id);		
		PlayerLogic.newInstance().downLine(client);
    	e.getCause().printStackTrace();  
    }  

    
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,ChannelStateEvent e)  throws Exception
	{ 
		log.error("尝试关闭链接，执行销毁操作 有两种情况:第一种 玩家还没有登录就断开socket,这种情况下clinet的player为空."
				+ "另一种情况,则是登录后断开.  两种处理不同,需要判断player是否为空");
		
		Channel tChannel  = ctx.getChannel();
		if(tChannel == null)
		{
			log.error("链接为空  retrun");
			return;
		}
	
		Integer id = tChannel.getId();
		GameClient client = World.newInstance().getChannel(id);	
		if(client == null)
		{		
			//单独清空
			World.newInstance().removeConnection(id);
			tChannel.close();
			tChannel = null;
			log.error("链接已经关闭 retrun");
			return;
		}
		PlayerLogic.newInstance().downLine(client);	
	}

} 