package netBase.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**

 */
@ChannelHandler.Sharable
public class ClientDispatcherHandler extends BaseDispatcherHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientDispatcherHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        logger.info("[channel:{}] 触发channelActive,建立会话", ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

       /* var session = SessionUtils.getSession(ctx);
        if (session != null) {
           // NetContext.getSessionManager().removeClientSession(session);

            // 如果是消费者inactive，还需要触发客户端消费者检查事件，以便重新连接
            //if (Objects.nonNull(session.getAttribute(AttributeType.CONSUMER))) {
              //  NetContext.getConfigManager().getRegistry().checkConsumer();
           // }
        }*/
        logger.warn("[channel:{}] is inactive", ctx);
    }

}

