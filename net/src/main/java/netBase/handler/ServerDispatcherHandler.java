package netBase.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import netBase.netty.BaseDispatcherHandler;

/**

 */
@ChannelHandler.Sharable
public class ServerDispatcherHandler extends BaseDispatcherHandler {

   // private static final Logger logger = LoggerFactory.getLogger(ServerDispatcherHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        var channel = ctx.channel();
        var sessionAttr = channel.attr(SESSION_KEY);
        var session = new Session(channel);
        var setSuccessful = sessionAttr.compareAndSet(null, session);
        if (!setSuccessful) {

            channel.close();
            return;
        }
        NetContext.getSessionManager().addServerSession(session);
        logger.info("[channel:{}] is active", ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        var session = SessionUtils.getSession(ctx);
        if (session == null) {
            return;
        }
        NetContext.getSessionManager().removeServerSession(session);
        EventContext.getEventBus().asyncSubmit(ServerSessionInactiveEvent.valueOf(session));
        logger.warn("[channel:{}] is inactive", ctx);
    }
}
