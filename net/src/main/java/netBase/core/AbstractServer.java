package netBase.core;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import netBase.TcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractServer {
    private static final Logger logger = LoggerFactory.getLogger(AbstractServer.class);
    // 配置服务端nio线程组，服务端接受客户端连接
    private EventLoopGroup bossGroup;

    // SocketChannel的网络读写
    private EventLoopGroup workerGroup;

    //用于初始化不同的通信服务子类 //此方法每次客户端连接都会调用，是为通道初始化的方法
    public abstract ChannelInitializer<? extends Channel> channelChannelInitializer();

    private String hostAddress;
    private int port;

    private ChannelFuture channelFuture;

    private Channel channel;

    public AbstractServer(String host,int port) {
        this.hostAddress = host;
        this.port = port;
    }

    public void start() {
        doStart(channelChannelInitializer());
    }

    protected synchronized void doStart(ChannelInitializer<? extends Channel> channelChannelInitializer) {




        //2.定义工作组:boss分发请求给各个worker:boss负责监听端口请求，worker负责处理请求（读写）
        int cpuNum = Runtime.getRuntime().availableProcessors();
        //bossGroup = new NioEventLoopGroup(Math.max(1, cpuNum / 4), new DefaultThreadFactory("netty-boss", true));
        bossGroup = new NioEventLoopGroup(cpuNum, new DefaultThreadFactory("netty-boss", true));
        workerGroup = new NioEventLoopGroup(cpuNum * 2, new DefaultThreadFactory("netty-worker", true));

        //1.定义server启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //3.定义工作组

        //option主要是设置的ServerChannel的一些选项，而childOption主要是设置的ServerChannel的子Channel的选项。
        //如果是Bootstrap的话，只会有option而没有childOption，所以设置的是客户端Channel的选项。
        //option主要是针对boss线程组，child主要是针对worker线程组
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)//4.设置通道channel
                //设置参数，TCP参数
                .option(ChannelOption.SO_REUSEADDR, true) //端口复用
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(channelChannelInitializer);  //5.添加handler，管道中的处理器，通过ChannelInitializer来构造
        // 绑定端口，同步等待成功
        // channelFuture = bootstrap.bind(hostAddress, port).sync();
        // 等待服务端监听端口关闭
        // channelFuture.channel().closeFuture().sync();

       // serverBootstrap.option(ChannelOption.SO_BACKLOG, 2048);         //连接缓冲池的大小,在一定程度上可以提高吞吐量。
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//维持链接的活跃，清除死链接


        // 异步
        channelFuture = serverBootstrap.bind(hostAddress, port);
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();

       // allServers.add(this);

        logger.info("TcpServer started at [{}:{}]", hostAddress, port);
    }


    public synchronized void shutdown() {

        try {
            if (channelFuture != null) {
                logger.error("server shutdown!");
                 //   channelFuture.channel().close().syncUninterruptibly();
                    // 5.监听关闭
                    channelFuture.channel().closeFuture().sync();  //等待服务关闭，关闭后应该释放资源
            }
            if (channel != null) {
                channel.close();
            }
        }
        catch (InterruptedException e) {
            logger.error("server start got exception!");
            e.printStackTrace();
        }finally {
            //8.优雅的关闭资源
            logger.error("server shutdown!2222222");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
