package netBase.Manager;

import io.netty.buffer.ByteBuf;

import netBase.Session;

/**
 * 消息管理类
 * @author djl
 * @date 2013.3.8
 */
public abstract class MessageManager
{
//private static final Logger log = Logger.getLogger(MessageManager.class);
	
	public static void send(Session client, ByteBuf buf)
	{

		if(client ==null)
		{
			//log.warn("该client对象为空，请检查");
			return;			
		}
		if(client.getChannel()==null)
		{
			//log.warn("该client的链接为空，尝试获取player关闭");
		//	PlayerLogic.newInstance().downLine(client);
			return;	
		}
		
		if (!client.getChannel().isOpen()) {
			//log.warn("该链接关闭，不能继续调用通道，请检查： 是否未根据客户端关闭而关闭map里的链接");
		//	PlayerLogic.newInstance().downLine(client);
			return;
		}
		
		//client.getChannel().write(buf);
	}

	
	/**
	 * 编码
	 */
	protected static ByteBuf encode(ByteBuf buffer, int moduleId, int opcode, Object... objects)
	{
		buffer.writeShort((short)moduleId);
		buffer.writeShort((short)opcode);
		

		for(Object obj: objects)
		{
		 	
			if(obj == null)
			{
				return null;
			}
			else if(obj instanceof Integer)
    		{
				int newObj = Integer.parseInt(String.valueOf(obj));
    			buffer.writeInt(newObj);
    		}
    	
			else if(obj instanceof Byte)
	    	{
				buffer.writeByte((byte)obj);
	    		//byte[] newObj = (byte[])obj;
	    		//buffer.writeBytes(newObj);
		    }
		    	
		    else if(obj instanceof String)
		    {
		    	String newObj = (String)obj;
            
                  for (int i = 0; i < newObj.length(); i++)
                  {
                    buffer.writeChar(newObj.charAt(i));
                  }
                  buffer.writeChar('\u0000');
		    }
		    	
		    else if(obj instanceof Short)
	    	{
	    		Short newShort = (Short)obj;
	    		buffer.writeShort(newShort);
	    	}
	    	
	    	else if(obj instanceof Long)
	    	{
	    		long newObj =Long.valueOf(obj.toString());
	    		buffer.writeLong(newObj);
	    	}		   
	    	
	    	else if (obj instanceof Character)
	    	{
	    		char newString =(char)obj;
	    		buffer.writeChar(newString);
	    		
	    	}
    	
    		else if (obj instanceof  Double)
    		{
    			double newObj =  Double.parseDouble(obj.toString());
    			buffer.writeDouble(newObj);
    		
    		}
    	
    		else if (obj instanceof Float)
    		{
    			float newObj=Float.parseFloat(obj.toString());
    			buffer.writeFloat(newObj);
    		}
    	}
    
    	return buffer;
  	}

	/**
	 * 
	 */
	public static ByteBuf encodeResult(ByteBuf buffer, int moduleId, int opcode, int result, Object... objects)
	{
		buffer.writeInt(result);
		encode(buffer, moduleId,opcode,objects);
		return buffer;
	}
  
  

/*//BIG_ENDIAN
  protected static ByteBuf createBuffer128()
  {
	  ByteBuf channelBuffer = ByteBuf.buffer(ByteOrder.BIG_ENDIAN, 128);
    return channelBuffer;
  }

  protected static ByteBuf createBuffer512()
  {
	  ByteBuf channelBuffer = ByteBuf.buffer(ByteOrder.BIG_ENDIAN, 512);
    return channelBuffer;
  }

  protected static ByteBuf createBuffer1024()
  {
	  ByteBuf channelBuffer = ByteBuf.buffer(ByteOrder.BIG_ENDIAN, 1024);
    return channelBuffer;
  }

  protected static ByteBuf createBuffer2048()
  {
	  ByteBuf channelBuffer = ByteBuf.buffer(ByteOrder.BIG_ENDIAN, 2048);
    return channelBuffer;
  }

  protected static ByteBuf createBuffer(int length)
  {
	  ByteBuf channelBuffer = ByteBuf.buffer(ByteOrder.BIG_ENDIAN, length);
    return channelBuffer;
  }*/
}
