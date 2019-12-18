package netBase.packet;


import netBase.packet.AbstractPacket;

/**
 *
 */
public abstract class SendablePacket extends AbstractPacket
{

	public SendablePacket()
	{
	}
	
	/**
	 * @param b
	 */
	protected final void writeB(byte b)
	{
		getBuffer().writeByte(b);
	}
	
	/**
	 * @param b
	 */
	protected final void writeS(short b)
	{
		getBuffer().writeShort(b);
	}
	
	
	/**
	 * @param b
	 */
	protected final void writeI(int b)
	{
		getBuffer().writeInt(b);
	}
	
	/**
	 * @param b
	 */
	protected final void writeL(long b)
	{
		getBuffer().writeLong(b);
	}
	
	
	/**
	 * @param b
	 */
	protected final void writeF(float b)
	{
		getBuffer().writeFloat(b);
	}
	
	/**
	 * @param b
	 */
	protected final void writeD(double b)
	{
		getBuffer().writeDouble(b);
	}
	
	
	/**
	 * @param b
	 */
	protected final void writeStr(String b)
	{
		for(int i = 0 ; i < b.length() ; i++)
		{
			getBuffer().writeChar(b.charAt(i));
		}
		
		getBuffer().writeChar('\000');
	}
	
	protected final void writeBS(byte[] bs)
	{
		getBuffer().writeBytes(bs);
	}

	
}
