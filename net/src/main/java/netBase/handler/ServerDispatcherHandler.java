package netBase.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import netBase.World;
import netBase.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

 */
@ChannelHandler.Sharable
public class ServerDispatcherHandler extends BaseDispatcherHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerDispatcherHandler.class);
  // private static final Logger log = Logger.getLogger(ServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        logger.info("[channel:{}] 触发channelActive,建立会话", ctx);

        super.channelActive(ctx);
        var channel = ctx.channel();

        var sessionAttr = channel.attr(SESSION_KEY);
        var session = new Session(channel);
        var setSuccessful = sessionAttr.compareAndSet(null, session);
        if (!setSuccessful) {

            channel.close();
            return;
        }

        World.newInstance().addChannel(session);
      //  NetContext.getSessionManager().addServerSession(session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        logger.error("尝试关闭链接，执行销毁操作 有两种情况:第一种 玩家还没有登录就断开socket,这种情况下clinet的player为空."
                + "另一种情况,则是登录后断开.  两种处理不同,需要判断player是否为空");

        Channel tChannel  = ctx.channel();
        if(tChannel == null)
        {
            logger.error("链接为空  retrun");
            return;
        }

        ChannelId id = tChannel.id();
        Session client = World.newInstance().getChannel(id);
        if(client == null)
        {
            //单独清空
            World.newInstance().removeConnection(id);
            tChannel.close();
            tChannel = null;
            logger.error("链接已经关闭 retrun");
            return;
        }
      //  PlayerLogic.newInstance().downLine(client);







       /* super.channelInactive(ctx);

        var session = SessionUtils.getSession(ctx);
        if (session == null) {
            return;
        }
        NetContext.getSessionManager().removeServerSession(session);
        EventContext.getEventBus().asyncSubmit(ServerSessionInactiveEvent.valueOf(session));
        logger.warn("[channel:{}] is inactive", ctx);*/
    }
}
