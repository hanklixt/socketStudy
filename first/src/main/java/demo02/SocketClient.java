package demo02;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author lxt
 * 双向通信，发送消息并接受消息
 */
@Slf4j
public class SocketClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 10086;
        //和服务端指定端口的程序进行连接
        Socket socket = new Socket(host, port);
        final OutputStream out = socket.getOutputStream();
        out.write("你好，这是今天给你发送的消息".getBytes("utf-8"));
        // out.flush();    这里是刷新缓冲区的意思，但是，网上查了一下似乎可有可无
        //单向关闭连接，这里是关闭向服务端发送消息的连接
        socket.shutdownOutput();
        final InputStream input = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder builder = new StringBuilder();
        while ((len = input.read(bytes)) != -1) {
            builder.append(new String(bytes, 0, len, "utf-8"));
        }
        log.info("这是服务端饭返回的消息:{}", builder.toString());
        input.close();
        out.close();
        socket.close();
    }
}
