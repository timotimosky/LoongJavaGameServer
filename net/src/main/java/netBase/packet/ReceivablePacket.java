package netBase.packet;

import java.io.Serializable;
import io.netty.buffer.ByteBuf;


/**
 *
 * @author djl
 */
public class ReceivablePacket extends AbstractPacket implements Serializable  , Cloneable
{
	
	private static final long serialVersionUID = 1L;
	
	
	public ReceivablePacket clone() throws CloneNotSupportedException
	{      return (ReceivablePacket)super.clone();
    }
	
	/**
	 *
	 */
	public ReceivablePacket(int module, short opcode, ByteBuf buff )
	{
		super( module,  opcode,  buff );
	}	
	
	public ReceivablePacket(){}

	/**
	 *
	 */
/*	//protected final void send(Object object)
	{
		this.getClient().getChannel().write(object);
	}*/

	
	/**
	 * byte
	 * @return Integer
	 */
	protected final int readB()
	{
		return getBuffer().readByte();
	}
	
	/**
	 * short
	 * @return Integer
	 */
	protected final int readS()
	{
		return getBuffer().readShort();
	}
	
	/**
	 * Integer
	 * @return Integer
	 */
	protected final int readI()
	{
		return getBuffer().readInt();
	}
	
	/**
	 * Long
	 * @return Long
	 */
	protected final long readL()
	{
		return getBuffer().readLong();
	}
	
	/**
	 * Float
	 * @return Float
	 */
	protected final float readF()
	{
		return getBuffer().readFloat();
	}
	
	
	/**
	 * Double
	 * @return Double
	 */
	protected final double readD()
	{
		return getBuffer().readDouble();
	}
	
	/**
	 * @return String
	 */
	protected final String readStr()
	{
		StringBuffer stringbuff = new StringBuffer();
		
		for (char c; (c = getBuffer().readChar()) != 0;)
			stringbuff.append(c);
		
		return stringbuff.toString();
	}
	
	/**
	 * byteArray
	 * @return
	 */
	protected final byte[] readBs()
	{
		byte[] _byte = new byte[getBuffer().capacity() - getBuffer().readerIndex()];
		
		getBuffer().readBytes(_byte);
		
		return _byte;
	}
	
	/**
	 * byteArray
	 */
	protected final byte[] readBs(int length)
	{
		byte[] _byte = new byte[length];
				
		getBuffer().readBytes(_byte);
		
		return _byte;
	}
	
	/**
	 * @return Object
	 */
	protected final Object readObj()
	{
		byte[] bytes = new byte[getBuffer().capacity() - getBuffer().readerIndex()];
		
		return bytes;
	}

}

