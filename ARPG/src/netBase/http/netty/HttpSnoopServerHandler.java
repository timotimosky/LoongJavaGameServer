package netBase.http.netty;

import static org.jboss.netty.handler.codec.http.HttpHeaders.getHost;
import static org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunkTrailer;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.handler.stream.ChunkedFile;
import org.jboss.netty.util.CharsetUtil;


//用Netty实现的一个简单的HTTP服务器，可以处理静态文件，上传下载文件，也可以用作登录的账号密码验证。

public class HttpSnoopServerHandler extends SimpleChannelUpstreamHandler {

	protected static Logger log = Logger.getLogger(HttpSnoopServerHandler.class); 
	
    private HttpRequest request;
     
    /*由于netty本身的性能需求，每次传输的字节数最大为1024个字节，所以如果文件内容小于1024个字节， 
    	只需一次请求就可以上传文件成功；如果文件内容大于1024个字节，需多次分片上传，用到httpchunk，分片处理机制（netty本身自带）。 
    	一次处理不玩，分多次上传。 */
    private boolean readingChunks;
    /** Buffer that stores the response content */
    private final StringBuilder buf = new StringBuilder();

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        if (!readingChunks) {
            HttpRequest request = this.request = (HttpRequest) e.getMessage();

            String uri = request.getUri();
            
            log.info("uri=="+uri);
            
            /**
             * 100 Continue
             * 是这样的一种情况：HTTP客户端程序有一个实体的主体部分要发送给服务器，但希望在发送之前查看下服务器是否会
             * 接受这个实体，所以在发送实体之前先发送了一个携带100
             * Continue的Expect请求首部的请求。服务器在收到这样的请求后，应该用 100 Continue或一条错误码来进行响应。
             */
            if (is100ContinueExpected(request)) {
                send100Continue(e);
            }

            buf.setLength(0);
            buf.append("VERSION: " + request.getProtocolVersion() + "\r\n");
            buf.append("HOSTNAME: " + getHost(request, "unknown") + "\r\n");
            buf.append("REQUEST_URI: " + request.getUri() + "\r\n\r\n");
            
            // 解析http头部
            for (Map.Entry<String, String> h: request.headers()) {
                buf.append("HEADER: " + h.getKey() + " = " + h.getValue() + "\r\n");
            }
            buf.append("\r\n");

            // 解析请求参数
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
            Map<String, List<String>> params = queryStringDecoder.getParameters();
            if (!params.isEmpty()) {
                for (Entry<String, List<String>> p: params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                        buf.append("PARAM: " + key + " = " + val + "\r\n");
                    }
                }
                buf.append("\r\n");
            }

            if (request.isChunked()) {
                readingChunks = true;
            } else {
                ChannelBuffer content = request.getContent();
                if (content.readable()) {
                    buf.append("CONTENT: " + content.toString(CharsetUtil.UTF_8) + "\r\n");
                }
                log.info(" 1---buf=="+buf);
               // writeResponse(e);
            }
        } else {// 为分块编码时
            HttpChunk chunk = (HttpChunk) e.getMessage();
            
            
            if (chunk.isLast()) {
                readingChunks = false;
                buf.append("END OF CONTENT\r\n");

                HttpChunkTrailer trailer = (HttpChunkTrailer) chunk;
                if (!trailer.trailingHeaders().names().isEmpty()) {
                    buf.append("\r\n");
                    for (String name: trailer.trailingHeaders().names()) {
                        for (String value: trailer.trailingHeaders().getAll(name)) {
                            buf.append("TRAILING HEADER: " + name + " = " + value + "\r\n");
                        }
                    }
                    buf.append("\r\n");
                }
                log.info("buf=="+buf);
                writeResponse(e);
            } else {
                buf.append("CHUNK: " + chunk.getContent().toString(CharsetUtil.UTF_8) + "\r\n");
            }
        }
    }

    private void writeResponse(MessageEvent e) {
    	// 解析Connection首部，判断是否为持久连接
        boolean keepAlive = isKeepAlive(request);

        // Build the response object.
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        response.setContent(ChannelBuffers.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        
        //response.setStatus(HttpResponseStatus.OK);
        // 服务端可以通过location首部将客户端导向某个资源的地址。
        // response.addHeader("Location", uri);

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.getContent().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // 得到客户端的cookie信息，并再次写到客户端.
        String cookieString = request.headers().get(COOKIE);
        if (cookieString != null) {
            CookieDecoder cookieDecoder = new CookieDecoder();
            Set<Cookie> cookies = cookieDecoder.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                CookieEncoder cookieEncoder = new CookieEncoder(true);
                for (Cookie cookie : cookies) {
                    cookieEncoder.addCookie(cookie);
                    response.headers().add(SET_COOKIE, cookieEncoder.encode());
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            CookieEncoder cookieEncoder = new CookieEncoder(true);
            cookieEncoder.addCookie("key1", "value1");
            response.headers().add(SET_COOKIE, cookieEncoder.encode());
            cookieEncoder.addCookie("key2", "value2");
            response.headers().add(SET_COOKIE, cookieEncoder.encode());
        }

        final String path = Config.getRealPath(request.getUri());
        File localFile = new File(path);
        // 如果文件隐藏或者不存在
        if (localFile.isHidden() || !localFile.exists()) {
            // 逻辑处理
            return;
        }
        // 如果请求路径为目录
        if (localFile.isDirectory()) {
            // 逻辑处理
            return;
        }
        RandomAccessFile raf = null;
        
        try {
            raf = new RandomAccessFile(localFile, "r");
            long fileLength = raf.length();
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(fileLength));
            Channel ch = e.getChannel();
            ch.write(response);
            // 这里又要重新温习下http的方法，head方法与get方法类似，但是服务器在响应中只返回首部，不会返回实体的主体部分
            if (!request.getMethod().equals(HttpMethod.HEAD)) {
                ch.write(new ChunkedFile(raf, 0, fileLength, 8192));//8kb
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            if (keepAlive) {
                response.headers().set(CONTENT_LENGTH, response.getContent().readableBytes());
            }
            if (!keepAlive) {
                e.getFuture().addListener(ChannelFutureListener.CLOSE);
            }
        }
        
        // Write the response.
        ChannelFuture future = e.getChannel().write(response);

        // Close the non-keep-alive connection after the write operation is done.
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static void send100Continue(MessageEvent e) {
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
        e.getChannel().write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
    
    
    public static class Config {
    	 
        public static String getRealPath(String uri) {
            StringBuilder sb=new StringBuilder("H:/taobaocodeWorkSpace/ARPGme20141102/web/");
            sb.append(uri);
            if (!uri.endsWith("/")) {
                sb.append('/');
            }
            log.info("文件位置为"+sb.toString());
            return sb.toString();
        }
    }
}

