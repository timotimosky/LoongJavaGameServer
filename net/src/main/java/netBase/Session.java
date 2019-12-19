package netBase;

import io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicLong;

/* 玩家的通信通道 和玩家实体的 封装类װ
 * @author  timosky
 * @date 2013-3-7
 */
public class Session {

	private static final AtomicLong index = new AtomicLong(0);

	/**
	 * 玩家当前的线程Id
	 */
	private volatile int threadId;


	private Channel channel;
/*	private PlayerEntity player;
	
	public PlayerEntity getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}*/

	public Session(Channel chl)
	{
		this.channel =chl;
	}
	
	/**
	 *
	 */
	public Channel getChannel()
	{
		return this.channel;
	}
	
	public void Clear()
	{
		channel = null;
	//	player = null;
	}
}
