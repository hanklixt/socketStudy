package demo02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lxt
 * 接收消息，接收消息并返回消息
 */
@Slf4j
public class SocketServer {
    public static void main(String[] args) throws IOException {
        int port = 10086;
        ServerSocket server = new ServerSocket(port);
        //如果程序没有连接到此端口，将会一直阻塞
        log.info("server 将一直等待连接的到来");
        final Socket socket = server.accept();
        final InputStream input = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = input.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        log.info("接收到服务端的消息:{}", sb.toString());
        //获取输出流
        final OutputStream out = socket.getOutputStream();
        out.write("你好，收到了你的消息，我表示很开心！！".getBytes("utf-8"));
        out.close();
        server.close();
        input.close();
        socket.close();
    }
}
