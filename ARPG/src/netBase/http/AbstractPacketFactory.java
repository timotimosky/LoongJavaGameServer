package netBase.http;

import java.util.HashMap;


/**
 * 数据包工厂
 * @author du'xiang
 *
 */
public abstract class AbstractPacketFactory<T extends HttpConnection>
{
	
	private HashMap<Integer, ReceivablePacket<T>> receivableMap = new HashMap<Integer, ReceivablePacket<T>>();
	
	private HashMap<Class<? extends SendablePacket<T>>, Integer> sendableMap = new HashMap<Class<? extends SendablePacket<T>>, Integer>();
	
	public AbstractPacketFactory()
	{
		initPacket();
	}
	
	public AbstractPacketFactory(HashMap<Integer, ReceivablePacket<T>> receivableMap,
			HashMap<Class<? extends SendablePacket<T>>, Integer> sendableMap)
	{
		this.receivableMap = receivableMap;
		
		this.sendableMap = sendableMap;
	}
	
	/**
	 * 初始化接收数据包队列
	 */
	protected abstract void initReceivable();
	
	
	/**
	 * 初始化发送数据包队列
	 */
	protected abstract void initSendable();
	
	/**
	 * 初始化数据包
	 */
	public void initPacket()
	{
		initReceivable();
		initSendable();
	}
	
	/**
	 * 增加接收数据包
	 * @param opcode
	 * @param rp
	 */
	public void addReceivable(int opcode,ReceivablePacket<T> rp)
	{
		this.receivableMap.put(opcode, rp);
	}
	
	/**
	 * 增加发送数据包
	 */
	public void addSendable(Class<? extends SendablePacket<T>> sb,int opcode)
	{
		this.sendableMap.put(sb, opcode);
	}
	
	
	/**
	 * 得到接收数据包
	 */
	public ReceivablePacket<T> getReceivable(int opcode)
	{
		ReceivablePacket<T> rp = this.receivableMap.get(opcode);
		if(rp == null)
		{
			return null;
		}
		else 
		{
			rp = rp.clonePacket();
			rp.setFactory(this);
			return rp;
		}
	}
	
	/**
	 * 得到发送的数据包
	 */
	public void getSendable(SendablePacket<T> packet)
	{
		int opcode = this.sendableMap.get(packet.getClass());
		
		packet.setOpcode(opcode);
	}
}
