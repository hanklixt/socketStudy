package demo01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket 服务端(基本示例代码)
 *
 * @author lxt
 */
@Slf4j
public class SocketServer {

    
    public static void main(String[] args) throws IOException {
        int port = 10086;
        ServerSocket server = new ServerSocket(port);
        log.info("准备接收客户端连接");
        final Socket socket = server.accept();
        final InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            final StringBuilder append = sb.append(new String(bytes, 0, len, "utf-8"));
        }
        log.info("收到客户端的信息:{}", sb.toString());
        inputStream.close();
        server.close();
        socket.close();
    }
}
