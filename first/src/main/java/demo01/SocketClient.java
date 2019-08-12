package demo01;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 客户端(基本示例代码)
 *
 * @author lxt
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        //要连接的服务端ip地址和端口
        String host = "127.0.0.1";
        int port = 10086;
        //连接服务端
        Socket socket = new Socket(host, port);
        //建立连接后获取输出流
        final OutputStream out = socket.getOutputStream();
        String msg = "hello ,i am zhangsan";
        socket.getOutputStream().write(msg.getBytes("UTF-8"));
        out.close();
        socket.close();

    }

}
