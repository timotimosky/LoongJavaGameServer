package netBase.netty;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;


@ChannelHandler.Sharable
public class BaseDispatcherHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        var session = SessionUtils.getSession(ctx);
        if (session == null) {
            return;
        }
        DecodedPacketInfo decodedPacketInfo = (DecodedPacketInfo) msg;
        NetContext.getDispatcherManager().receive(session, decodedPacketInfo.getPacket(), decodedPacketInfo.getPacketAttachment());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        var channel = ctx.channel();

        if (!SessionUtils.isActive(channel)) {
            channel.close();
        }

        logger.error("未知异常", cause);
    }

}
