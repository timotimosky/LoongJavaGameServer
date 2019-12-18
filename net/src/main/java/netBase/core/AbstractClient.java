package netBase.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import netBase.util.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static netBase.handler.BaseDispatcherHandler.SESSION_KEY;
/**

 */
public abstract class AbstractClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractClient.class);

    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 1
            , new DefaultThreadFactory("netty-client", true));

    private String hostAddress;
    private int port;

    private Bootstrap bootstrap;

    public AbstractClient(String host,int port) {
        this.hostAddress = host;
        this.port = port;
    }

    public abstract ChannelInitializer<? extends Channel> channelChannelInitializer();

    public synchronized Session start() {
        return doStart(channelChannelInitializer());
    }

    private synchronized Session doStart(ChannelInitializer<? extends Channel> channelChannelInitializer) {

        //1.定义client启动类
        this.bootstrap = new Bootstrap();

        //2.
        this.bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(channelChannelInitializer());

        //连接服务端
        var channelFuture = bootstrap.connect(hostAddress, port);
        channelFuture.syncUninterruptibly();

        if (channelFuture.isSuccess()) {
            if (channelFuture.channel().isActive()) {
                var channel = channelFuture.channel();
                var sessionAttr = channel.attr(SESSION_KEY);
                var session = new Session(channel);
                var setSuccessful = sessionAttr.compareAndSet(null, session);
                if (setSuccessful) {
                   // NetContext.getSessionManager().addClientSession(session);
                    logger.info("TcpClient started at [{}]", session.getChannel().localAddress());
                    return session;
                } else {
                    channel.close();
                }
            }
        } else if (channelFuture.cause() != null) {
         //   logger.error(ExceptionUtils.getMessage(channelFuture.cause()));
        } else {
            logger.error("启动客户端[client:{}]未知错误", this);
        }
        return null;
    }


    public synchronized static void shutdown() {
      //  AbstractServer.shutdownEventLoopGracefully(nioEventLoopGroup);
    }

}
