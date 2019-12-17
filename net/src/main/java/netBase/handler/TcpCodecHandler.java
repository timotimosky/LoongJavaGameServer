package netBase.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import netBase.ReceivablePacket;

import java.util.List;

/**
 */
public class TcpCodecHandler extends ByteToMessageCodec<ReceivablePacket> {

    //private static final Logger logger = LoggerFactory.getLogger(TcpPacketCodecHandler.class);

    private int length;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {

        if (buffer.readableBytes() < 4) {
            //log.warn("message short than four!");
            return;
        }

        buffer.markReaderIndex();

        int dataLength = buffer.readInt();

        if (buffer.readableBytes() < dataLength)
        {
            //log.warn("message short than dataLength+4!  dataLength=="+dataLength);
            buffer.resetReaderIndex();
            return;
        }

        ByteBuf newbuffer = buffer.readBytes(dataLength);

        short  module = buffer.getShort(4);
        short opcode =  buffer.getShort(6);

        ReceivablePacket pack  = new ReceivablePacket(module, opcode, newbuffer);

        //屏蔽心跳打印
        // if (!(module==1 && opcode== 200)) {
        // log.info("receive.."+ctx.getChannel().getId()+" length"+ dataLength+" module"+module+"  opcode"+opcode);
        //}
        out.add(pack);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ReceivablePacket packetInfo, ByteBuf outbuf) {


        ByteBuf chnBuf= packetInfo.getBuffer();

        int datalength = chnBuf.readableBytes();
        //log.infof("编码 datalength========================"+datalength);

        outbuf.writeInt(datalength+4);
        outbuf.writeBytes(chnBuf);



        try {

           // NetContext.getProtocolService().write(out, packetInfo.getPacket(), packetInfo.getPacketAttachment());
        } catch (Exception e) {
          //  logger.error("[{}]编码异常", packetInfo.getPacket(), e);
        }
    }

}

