package netBase.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelId;
import netBase.World;
import netBase.util.Session;

/**
 * 网络数据包
 * 
 * 其实可以用client代替playerID作为key
 * @author TimoSky..
 *
 */
public abstract class AbstractPacket
{
	protected short opcode;//
	
	private ByteBuf buffer;//
		
	private int module;//
	
	private Session client;//
	
	
	/**
	 *
	 */
	public AbstractPacket(int module, short opcode, ByteBuf buff )
	{
		this.module = module;
		this.opcode = opcode;
		this.buffer = buff; 
	}
	public AbstractPacket(){}
	
	public AbstractPacket(short opcode)
	{
		
		this.opcode = opcode;
	}
	
	public void addClient(ChannelId id)
	{
		this.client = World.newInstance().getChannel(id);
	}
	
	
	
	/*-------------get set方法------------*/
	
	
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

/*	public final GameClient getClient() {
		if(this.client == null)
			this.client = World.newInstance().getChannel(this.opcode);
		return this.client;
	}*/

	public void setClient(Session client) {
		this.client = client;
	}

	public short getOpcode()
	{
		return opcode;
	}
	
	public void setOpcode(short opcode)
	{
		this.opcode = opcode;
	}


	public  ByteBuf getBuffer()
	{
		return buffer;
	}

	public final void setBuffer(ByteBuf buff)
	{
		this.buffer = buff;
	}
	
	
}
