/*
package netBase.http;


import org.jboss.netty.buffer.ChannelBuffer;

*/
/**
 * 网络数据包
 * 
 * 其实可以用client代替playerID作为key
 * @author TimoSky..
 *
 *//*

public abstract class AbstractPacketHttp <T extends HttpConnection>
{
	protected int opcode;//
	
	private ChannelBuffer buffer;//
		
	private int module;//
	
	private T client;//
	
	
	*/
/**
	 *
	 *//*

	public AbstractPacketHttp(int module, int opcode, ChannelBuffer buff)
	{
		this.module = module;
		this.opcode = opcode;
		this.buffer = buff; 
	}
	public AbstractPacketHttp(){}
	
	public AbstractPacketHttp(int opcode)
	{
		
		this.opcode = opcode;
	}
	
	public void addClient(int id)
	{
		//this.client = World.newInstance().getChannel(id);
	}
	
	
	
	*/
/*-------------get set方法------------*//*

	
	
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public final T  getClient() {

		return this.client;
	}

	public void setClient(T client) {
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


	public  ChannelBuffer getBuffer()
	{
		return buffer;
	}

	public final void setBuffer(ChannelBuffer buff)
	{
		this.buffer = buff;
	}
	
	
}
*/
