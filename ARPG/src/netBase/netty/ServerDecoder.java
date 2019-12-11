package netBase.netty;
import netBase.ReceivablePacket;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author DJL
 * @date 2013-1-19
 */ 
public class ServerDecoder extends FrameDecoder {
	
	private static final Logger log = Logger.getLogger(ServerDecoder.class);
	
	@Override
    protected  Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {		
		
        if (buffer.readableBytes() < 4) {  
        	
        	log.warn("message short than four!");
            return null;  
        }  
       
       buffer.markReaderIndex();
       int dataLength = buffer.readInt();

        if (buffer.readableBytes() < dataLength) 
        {  
        	log.warn("message short than dataLength+4!  dataLength=="+dataLength);
        	buffer.resetReaderIndex();
            return null;   
        } 
        
        ChannelBuffer newbuffer = buffer.readBytes(dataLength);
     
        short  module = buffer.getShort(4);             
        short opcode =  buffer.getShort(6);         
 
        ReceivablePacket pack  = new ReceivablePacket(module, opcode, newbuffer);
        
        //屏蔽心跳打印
       // if (!(module==1 && opcode== 200)) {
           // log.info("receive.."+ctx.getChannel().getId()+" length"+ dataLength+" module"+module+"  opcode"+opcode);
		//}
 
        return pack;  
    }
}