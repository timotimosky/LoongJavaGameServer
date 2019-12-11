package netBase.http.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import netBase.http.thread.ThreadNetWorkPoolManager;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


//启动Http访问
//是否和socket不同？ 
public class HttpServer {
	
	static Logger log = Logger.getLogger(HttpServer.class);
	
	public void init(int port)
	{
		try 
		{
			ChannelFactory HTTPCHANNEL_FACTORY = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());
		  
			/**HTTP方式(同TCP)*/
		  	ChannelFactory nowFa = new NioServerSocketChannelFactory(ThreadNetWorkPoolManager.getInstance(), 1, 
				  ThreadNetWorkPoolManager.getInstance(), 1);
				  
			ServerBootstrap bootstrap = new ServerBootstrap(nowFa);
			
			
			bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
			bootstrap.bind(new InetSocketAddress(port));
		} 
		catch (Exception e) 
		{
			log.error("Http服务启动失败！", e);
		}
		log.info("http服务开启>>>>>请访问http://127.0.0.1:8484/index.html进行测试！\n\n");
	}

}
