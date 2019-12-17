package netBase;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public abstract class AbstractServer {

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

    public void start() {
        doStart(channelChannelInitializer());
    }

    protected synchronized void doStart(ChannelInitializer<? extends Channel> channelChannelInitializer) {

        //1.定义server启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //2.定义工作组:boss分发请求给各个worker:boss负责监听端口请求，worker负责处理请求（读写）
        int cpuNum = Runtime.getRuntime().availableProcessors();
        bossGroup = new NioEventLoopGroup(Math.max(1, cpuNum / 4), new DefaultThreadFactory("netty-boss", true));
        workerGroup = new NioEventLoopGroup(cpuNum * 2, new DefaultThreadFactory("netty-worker", true));

        //3.定义工作组
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)//4.设置通道channel
                //设置参数，TCP参数
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
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

        //logger.info("TcpServer started at [{}:{}]", hostAddress, port);
    }


    public synchronized void shutdown() {

        try {
            // 5.监听关闭
            channelFuture.channel().closeFuture().sync();  //等待服务关闭，关闭后应该释放资源
        }
        catch (InterruptedException e) {
            System.out.println("server start got exception!");
            e.printStackTrace();
        }finally {
            //8.优雅的关闭资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}