package netBase;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import netBase.core.AbstractServer;
import netBase.handler.ServerDispatcherHandler;
import netBase.handler.ServerIdleHandler;
import netBase.handler.TcpCodecHandler;
import org.apache.log4j.Logger;

/**
 */
public class TcpServer extends AbstractServer {

    private static final Logger logger =Logger.getLogger(TcpServer.class);

    public TcpServer(String host, int port ) {
        super(host,port);
    }

    @Override
    public ChannelInitializer<SocketChannel> channelChannelInitializer() {
        return new TcpChannelHandler();
    }


    private static class TcpChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel channel) {
           // logger.debug("initChannel");

            System.out.println(" TcpServer    initChannel");
            channel.pipeline().addLast(new IdleStateHandler(0, 0, 180));
            channel.pipeline().addLast(new ServerIdleHandler());
            channel.pipeline().addLast(new TcpCodecHandler());
            channel.pipeline().addLast(new ServerDispatcherHandler());
        }
    }
}

