package netBase.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import netBase.packet.ReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 */
public class TcpCodecHandler extends ByteToMessageCodec<ReceivablePacket> {

    private static final Logger logger = LoggerFactory.getLogger(TcpCodecHandler.class);

    private int length;

    //解码
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {


        logger.info(" TcpCodecHandler ---触发解码");


        //todo: 这里改成Unity的解码那样 避免这4位数据的BUG
        if (buffer.readableBytes() < 4) {
            logger.warn("message short than four!");
            return;
        }

        buffer.markReaderIndex();

        int dataLength = buffer.readInt();

        if (buffer.readableBytes() < dataLength)
        {
            logger.warn("message short than dataLength+4!  dataLength=="+dataLength);
            buffer.resetReaderIndex();
            return;
        }

        ByteBuf newbuffer = buffer.readBytes(dataLength);

        short module = buffer.getShort(4);
        short opcode =  buffer.getShort(6);

        ReceivablePacket pack  = new ReceivablePacket(module, opcode, newbuffer);

        //屏蔽心跳打印
         //if (!(module==1 && opcode== 200)) {
             logger.info("receive.."+ctx.channel().id()+" length"+ dataLength+" module"+module+"  opcode"+opcode);
        // }
        out.add(pack);
    }

    //编码
    @Override
    protected void encode(ChannelHandlerContext ctx, ReceivablePacket packetInfo, ByteBuf outbuf) {


        ByteBuf chnBuf= packetInfo.getBuffer();

        int datalength = chnBuf.readableBytes();

        logger.info("编码 datalength========================"+datalength);

        outbuf.writeInt(datalength+4);
        outbuf.writeBytes(chnBuf);



        try {

           // NetContext.getProtocolService().write(out, packetInfo.getPacket(), packetInfo.getPacketAttachment());
        } catch (Exception e) {
          //  logger.error("[{}]编码异常", packetInfo.getPacket(), e);
        }
    }

}

