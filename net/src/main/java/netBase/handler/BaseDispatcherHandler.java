package netBase.handler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import netBase.AloneNetMap;
import netBase.packet.ReceivablePacket;
import netBase.World;
import netBase.util.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class BaseDispatcherHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(BaseDispatcherHandler.class);

    public static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("session-key");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws CloneNotSupportedException {

        logger.info(" 触发 channelRead");
        if(!(msg instanceof ReceivablePacket))
        {
            return;
        }

        ReceivablePacket packet = (ReceivablePacket) msg;

        if(packet.getBuffer().capacity()< 3)
        {
            return ;
        }

        ChannelId id = ctx.channel().id();
        //添加映射
        packet.addClient(id);

        //log.infoln( ctx.getChannel().getId() + "的通道  messageReceived");

        Session client = World.newInstance().getChannel(id);

        /*将消息包放入玩家对应的消息队列*/
        AloneNetMap.netMap.addPack(client,packet);



     /*   var session = SessionUtils.getSession(ctx);
        if (session == null) {
            return;
        }
        DecodedPacketInfo decodedPacketInfo = (DecodedPacketInfo) msg;
        NetContext.getDispatcherManager().receive(session, decodedPacketInfo.getPacket(), decodedPacketInfo.getPacketAttachment());*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        System.out.println(" 触发 exceptionCaught");


        //通信异常,就不能调用通知该链接关闭通信的 		SceneMessage.GC_SelfLeaveSence(client);了
        //不然会导致写入socket出错

       // log.error("通信异常,执行删除map操作,不然出错----"+e);

        ChannelId id = ctx.channel().id();

        //TODO：客户端不需要这样
        Session client = World.newInstance().getChannel(id);
       // PlayerLogic.newInstance().downLine(client);
        cause.printStackTrace();



       /* var channel = ctx.channel();

        if (!SessionUtils.isActive(channel)) {
            channel.close();
        }

        logger.error("未知异常", cause);*/
    }

}
