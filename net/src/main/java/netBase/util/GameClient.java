package netBase.util;

import io.netty.channel.Channel;

/* 玩家的通信通道 和玩家实体的 封装类װ
 * @author  timosky
 * @date 2013-3-7
 */
public class GameClient {

	private Channel channel;
	private PlayerEntity player;
	
	public PlayerEntity getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}

	public  GameClient(Channel chl)
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
		player = null;
	}
}
