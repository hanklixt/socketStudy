package demo04;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lxt
 * 通过指定长度读取消息
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
        byte[] bytes;
        int first;
        //每条消息写了3次，读的时候也要读3次
        while (true) {
            //返回0到255的字节
            first = input.read();
            log.info(first + "");
            //读完之后推出
            if (first == -1) {
                break;
            }
            //返回0到255的字节
            final int second = input.read();
            int length = (first << 8) + second;
            //构造指定长的byte数组
            bytes = new byte[length];
            //读到byte中去
            input.read(bytes);
            log.info("接受到客户端的消息" + new String(bytes, "utf-8"));
        }
        input.close();
        server.close();
        socket.close();
    }
}
