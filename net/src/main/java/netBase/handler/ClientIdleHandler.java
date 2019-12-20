package netBase.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import netBase.packet.AbstractPacket;
import netBase.packet.HeartBeatPacket;
import netBase.packet.SendablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2019-08-25 15:17
 */
@ChannelHandler.Sharable
public class ClientIdleHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientIdleHandler.class);

   private static final HeartBeatPacket heartbeatPacket = new HeartBeatPacket();

   //定时回调
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            //如果读写都闲置，才发一次心跳，减少心跳量
            if (event.state() == IdleState.ALL_IDLE) {
                logger.info("client sends heartbeat packet to [server:{}]", ctx);

                ByteBuf writeBuffer = Unpooled.directBuffer();
                writeBuffer.writeInt(10);
                writeBuffer.writeShort(1);
                writeBuffer.writeShort(200);
                writeBuffer.writeShort(1001);
                writeBuffer.writeInt(123456);


                ctx.channel().writeAndFlush(writeBuffer);
            }
        }

    }
}
