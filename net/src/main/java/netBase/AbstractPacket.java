package netBase;

import io.netty.buffer.ByteBuf;
import netBase.util.GameClient;

/**
 * 网络数据包
 * 
 * 其实可以用client代替playerID作为key
 * @author TimoSky..
 *
 */
public abstract class AbstractPacket
{
	protected int opcode;//
	
	private ByteBuf buffer;//
		
	private int module;//
	
	private GameClient client;//
	
	
	/**
	 *
	 */
	public AbstractPacket(int module, int opcode, ByteBuf buff )
	{
		this.module = module;
		this.opcode = opcode;
		this.buffer = buff; 
	}
	public AbstractPacket(){}
	
	public AbstractPacket(int opcode)
	{
		
		this.opcode = opcode;
	}
	
	public void addClient(int id)
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

	public final GameClient getClient() {
		if(this.client == null)
			this.client = World.newInstance().getChannel(this.opcode);
		return this.client;
	}

	public void setClient(GameClient client) {
		this.client = client;
	}

	public int getOpcode() 
	{
		return opcode;
	}
	
	public void setOpcode(int opcode)
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
