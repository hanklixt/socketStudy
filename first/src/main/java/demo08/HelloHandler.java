package demo08;

import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.channel.*;

/**
 * @author lxt
 * @date 2019/9/3 0003
 */
@Slf4j
public class HelloHandler extends SimpleChannelHandler {

    /**
     * 异常捕获
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        log.info("出现了异常");
        log.info(e.getCause().getMessage());
        super.exceptionCaught(ctx, e);
    }

    /**
     * 退出连接
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {

        log.info("channelDisconnected");
        final Channel channel = e.getChannel();
        super.channelDisconnected(ctx, e);
    }

    /**
     * 管道连接
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        log.info("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 消息处理
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
      //       int i=1/0;
      //  final ChannelBuffer buffer = (ChannelBuffer) ;
        //   final String s = new String(buffer.array());
        //  pipeline.addLast("decoder",new StringDecoder());!!!这句在主程序中
      //设置管道工厂时设置了以上，可以直接取成string
        String s= (String) e.getMessage();
        log.info("接收到了消息{}",s);
        //回写数据,不设置解码时
        //必须是ChannelBuffer对象
       //final ChannelBuffer channelBuffer = ChannelBuffers.copiedBuffer("hello".getBytes());
       //ctx.getChannel().write(channelBuffer);
        ctx.getChannel().write("hi");
        super.messageReceived(ctx, e);
    }

    /**
     * 管道关闭
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        log.info("channelClosed");
        super.channelClosed(ctx, e);
    }


}
