package demo05;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lxt
 * @date 2019-09-02
 * <p>客户端与服务端建立长连接基本demo,单线程</p>
 */
@Slf4j
public class OioSocketServer {
    public static void main(String[] args) throws IOException {
        int port=10086;
        ServerSocket serverSocket=new ServerSocket(port);
        log.info("服务端启动");
        //轮询
        while (true){
            final Socket socket = serverSocket.accept();
            log.info("新来了一个客户端");
            handler(socket);
        }

    }

    /**
     * 消息处理
     */
    public  static void handler(Socket socket){
        try {
            final InputStream inputStream = socket.getInputStream();
            while (true){
                byte[] bytes=new byte[1024];
                //读取数据(阻塞)，等待客户端发送消息
                final int read =  inputStream.read(bytes);
                StringBuilder builder=new StringBuilder();
                if (read!=-1){
                  builder.append(new String(bytes,"utf-8"));
                }else {
                    final OutputStream outputStream = socket.getOutputStream();
                    final String resp = "接收完你的消息,表示很开心";
                    outputStream.write(resp.getBytes("utf-8"));
                    outputStream.close();
                    break;
                }
                log.info(builder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.info("消息处理失败");
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
