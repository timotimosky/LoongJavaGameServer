package netBase.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 心跳handler
* */
@ChannelHandler.Sharable
public class ServerIdleHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerIdleHandler.class);


    //netty4使用IdleStateHandler来检测连接状态
    // 我们的ServerIdleHandler 也重写了userEventTriggered  用于检测心跳
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        logger.info(" ServerIdleHandler ----- 触发心跳回调");
        //TODO：这里心跳处理有BUG
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
               //logger.warn("[channel:{}] is time out for close", ctx);
            }
            ctx.channel().close();
        }

    }
}
