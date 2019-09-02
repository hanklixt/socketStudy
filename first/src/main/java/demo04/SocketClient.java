package demo04;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author lxt
 * 双向通信，通过指定长度读取消息
 */
@Slf4j
public class SocketClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 10086;
        //和服务端指定端口的程序进行连接
        Socket socket = new Socket(host, port);
        final OutputStream out = socket.getOutputStream();
        String message = "第一条消息大声地说";
        byte[] sendByte = message.getBytes("utf-8");
        //右移8位,0到256位移位为0，不知道什么意思，有什么必要
        out.write(sendByte.length >> 8);
        out.write(sendByte.length);
        out.write(sendByte);
        out.flush();
        message = "第二条消息";
        sendByte = message.getBytes("utf-8");
        log.info(sendByte.length + "");
        out.write(sendByte.length>>8);
        out.write(sendByte.length);
        out.write(sendByte);
        out.flush();
        message = "第三条消息";
        sendByte = message.getBytes("utf-8");
        log.info(sendByte.length + "");
        out.write(sendByte.length>>8);
        out.write(sendByte.length);
        out.write(sendByte);
        out.close();
        socket.close();
    }
}
