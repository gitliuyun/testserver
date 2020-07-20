import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @Author weizhenyang
 * @Date 2020/7/20 17:07
 **/
public class MsgPckEncode extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buf)
            throws Exception {
        // TODO Auto-generated method stub
        MessagePack pack = new MessagePack();

        byte[] write = pack.write(msg);

        buf.writeBytes(write);

    }
}
