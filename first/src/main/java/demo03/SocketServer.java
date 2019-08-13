package demo03;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
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
        final OutputStream out = socket.getOutputStream();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, "utf-8"));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null && !"end".equals(line)) {
            builder.append(line);
        }
        log.info("收到客户端的信息" + builder.toString());
        out.write("你好，收到了你的消息，我表示很开心！！".getBytes("utf-8"));
        out.close();
        server.close();
        input.close();
        socket.close();
    }
}
