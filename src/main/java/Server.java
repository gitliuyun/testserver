import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author weizhenyang
 * @Date 2020/7/20 17:12
 **/
public class Server {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(40009)
                    .childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            // TODO Auto-generated method stub
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(10,0,0));
                            pipeline.addLast(new MsgPckDecode());
                            pipeline.addLast(new MsgPckEncode());
                            pipeline.addLast(new Server3Handler());
                        }
                    });
            System.out.println("start server 30001 --");
            ChannelFuture sync = serverBootstrap.bind().sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //优雅的关闭资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}