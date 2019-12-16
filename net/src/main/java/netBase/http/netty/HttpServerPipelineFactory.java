package netBase.http.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

public class HttpServerPipelineFactory implements ChannelPipelineFactory  {

	 public ChannelPipeline getPipeline() throws Exception{  
		    {
		    	
		    	ChannelPipeline pipeline = Channels.pipeline();

		        pipeline.addLast("decoder", new HttpRequestDecoder());

//		        pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));//放在decoder以后

		        pipeline.addLast("encoder", new HttpResponseEncoder());

		        
		        pipeline.addLast("chunkedWriter", new ChunkedWriteHandler()); //分片handler，用于文件下载，可以关闭
		        pipeline.addLast("deflater", new HttpContentCompressor());//压缩器，可以关闭
		        
		        //pipeline.addLast("handler", new HttpServerHandler());  
		        pipeline.addLast("handler", new HttpSnoopServerHandler());    

//		        设置好aggregator和chunkedWriter后,可上传、下载附件，调用如下

//		        Channel ch = ...;

//		         ch.write(new ChunkedFile(new File("video.mkv"));

		//

		        //以下是HTTPS的设置
		       /* SSLEngine engine = HttpSslContextFactory.getServerContext().createSSLEngine();
		        engine.setUseClientMode(false);    //非客户端模式
		        pipeline.addLast("ssl", new SslHandler(engine));*/

		        return pipeline;
		    }
}
}