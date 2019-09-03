package demo08.server;

import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lxt
 * @date 2019/9/3 0003
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        //服务类
        ServerBootstrap bootstrap=new ServerBootstrap();

        //这里简单的使用，不用ThreadPoolExecutors创建了
        final ExecutorService boss = Executors.newCachedThreadPool();
        final ExecutorService worker = Executors.newCachedThreadPool();

        //设置nioSocket的工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        //设置管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                final ChannelPipeline pipeline = Channels.pipeline();
                //设置解码器，不用每次都写一样的代码
                pipeline.addLast("decoder",new StringDecoder());
                //设置编码器，给客户端回写数据的时候不用每次都写个ChannelBuffer
                pipeline.addLast("encoder",new StringDecoder());
                pipeline.addLast("ha",new HelloHandler());
                return pipeline;
            }
        });
        bootstrap.bind(new InetSocketAddress(10086));
        log.info("服务启动了");

    }
}
