import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector使用范例
 *
 * @author Bullet
 * @time 2017-06-04 2:48
 */
public class SelecterCenter {

  public static void main(String[] args) throws IOException {

    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.socket().bind(new InetSocketAddress(8888));
    Selector selector = Selector.open();

    ssc.configureBlocking(false);
    ssc.register(selector, SelectionKey.OP_ACCEPT);

    int i = 0;
    while (i < 10) {
//      System.out.println("Iterator: " + (i+1));
      int selectNum = selector.select();
      if (selectNum == 0) {
        System.out.println("No Channel ready!");
      } else {
        Set<SelectionKey> selectKeys = selector.selectedKeys();
        System.out.println(selectKeys.size());
        doIter(selector, selectKeys);
      }
      i++;
    }





  }

  private static void doIter(Selector selector, Set<SelectionKey> selectKeys) throws IOException {
    Iterator<SelectionKey> it = selectKeys.iterator();

    while (it.hasNext()) {
      SelectionKey key = it.next();
      if (key.isAcceptable()) {
        System.out.println("Key is Acceptable");      // ServerSocketChannel
        SocketChannel csc = ((ServerSocketChannel) key.channel()).accept();
        csc.configureBlocking(false);
        csc.register(selector, SelectionKey.OP_READ);
        it.remove();
      } else if (key.isReadable()) {
        System.out.println("Key is readable");
        ByteBuffer bb = ByteBuffer.allocate(1024);
        SocketChannel csc = ((SocketChannel) key.channel());
        System.out.println(csc.read(bb));
        bb.flip();

        while (bb.hasRemaining()) {
          byte b = bb.get();
          System.out.println(Integer.toHexString(b) + ": " + (char)b);
        }
        it.remove();
      } else {
        System.out.println("No ready");
      }
    }
  }
}
