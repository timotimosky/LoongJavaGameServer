package netBase;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import netBase.core.AbstractClient;
import netBase.handler.ClientDispatcherHandler;
import netBase.handler.ClientIdleHandler;
import netBase.handler.TcpCodecHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class TcpClient extends AbstractClient {

    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    public TcpClient(String host, int port ) {
        super(host,port);
    }

    @Override
    public ChannelInitializer<? extends Channel> channelChannelInitializer() {
        return new TcpChannelInitHandler();
    }


    private static class TcpChannelInitHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel channel) {
            logger.debug("TcpClient initChannel");

            channel.pipeline().addLast(new IdleStateHandler(0, 0, 5));
            channel.pipeline().addLast(new ClientIdleHandler());
            channel.pipeline().addLast(new TcpCodecHandler());
            channel.pipeline().addLast(new ClientDispatcherHandler());
        }
    }


}
