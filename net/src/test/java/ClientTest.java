import netBase.TcpClient;
import netBase.TcpServer;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Objects;
@Ignore
public class ClientTest {
    // private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server_config.xml");

    @Test
    public void startClient() throws InterruptedException {
        //    SessionUtils.printSessionInfo();
//        startClient1();

        System.out.println("开始测试 startClient");

        var server0 = new TcpClient("127.0.0.1",10020);
        server0.start();
        // Thread.sleep(Long.MAX_VALUE);

        // var server1 = new TcpServer("127.0.0.1",8081);
        Thread.sleep(Long.MAX_VALUE);



    }

}




