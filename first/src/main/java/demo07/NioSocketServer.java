package demo07;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author lxt
 * @date 2019/9/2 0002
 */
@Slf4j
public class NioSocketServer {

    /**
     * 通道管理器
     */
    private Selector selector;

    /***
     * 获得一个serverSocket的通道，并对通道做一些初始化的工作
     * @param port
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        //获得一个ServerSocket通道
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //设置通道为非阻塞
        serverChannel.configureBlocking(false);
        //将该通道对应的ServerSocket绑定到对应的端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        //获得一个通道管理器
        this.selector = Selector.open();
        //将通道管理器和该通道绑定,并且为该通道注册SelectionKey.OP_ACCEPT时间，注册该事件后，当该事件到达后，
        //selector.select()会返回，如果该事件没有到达，selector.select()将会一直阻塞
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
//        SelectionKey.OP_ACCEPT —— 接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
//        SelectionKey.OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
//        SelectionKey.OP_READ —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
//        SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
//        ————————————————
//        原文链接：https://blog.csdn.net/robinjwong/article/details/41794413
    }

    /**
     * 使用轮询的方式检查Selector上是否有待处理的事件，如果有，则进行处理
     * @throws IOException
     */
    public void listen() throws IOException {
       log.info("服务端启动成功");
       //继续访问Selector
       while (true){
           //当注册的事件到达时，方法返回，否则，方法会一直阻塞
           selector.select();
           //获得 selctor 中选中的项的迭代器，选中的项为注册的事件
           final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
           while (iterator.hasNext()){
               final SelectionKey next = iterator.next();
               //删除已选的key,以防重复处理
               iterator.remove();
               //消息处理
                handler(next);
           }
       }

    }

    /**
     * 处理客户连接请求
     * @param selectionKey
     */
    public void handler(SelectionKey selectionKey) throws IOException {
        //客户端请求连接事件
        if (selectionKey.isAcceptable()){
            handleAccept(selectionKey);
            //获得了可读的事件
      }else if (selectionKey.isReadable()){
           handleRead(selectionKey);
      }
    }

    /**
     * 处理客户端请求连接事件连接事件
     * @param key
     * @throws IOException
     */
    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel server= (ServerSocketChannel) key.channel();
        //获得和客户端连接的通道
        final SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        log.info("新的客户端连接");
        //在和客户端连接成功后，为了可以接受到客户端的信息，需要给通道设置读的权限
        channel.register(this.selector,SelectionKey.OP_READ);
    }

    /**
     * 处理可读的请求
     * @param key
     */
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel serverSocketChannel= (SocketChannel) key.channel();
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        final int read = serverSocketChannel.read(buffer);
        if (read>0){
            final byte[] array = buffer.array();
            final String msg = new String(array, "utf-8").trim();
            log.info("服务端收到消息"+msg);
            final ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes("utf-8"));
            //消息回复给客户端
            serverSocketChannel.write(wrap);
        }else {
            final String end = "客户端退出连接";
            log.info(end);
            key.cancel();
            //todo 待优化
        }

    }
    public static void main(String[] args) throws IOException {
      NioSocketServer server=new NioSocketServer();
      server.initServer(10086);
      server.listen();
    }

}
