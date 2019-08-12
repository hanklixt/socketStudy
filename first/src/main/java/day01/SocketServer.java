package day01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket 服务端
 *
 * @author lxt
 */
@Slf4j
public class SocketServer {

    public static void main(String[] args) throws IOException {
        int port = 10086;
        //实例化
        ServerSocket server = new ServerSocket(port);
        log.info("准备接收客户端连接");
        final Socket socket = server.accept();
        final InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];

    }
}
