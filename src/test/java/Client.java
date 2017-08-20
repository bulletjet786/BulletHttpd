import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 用于测试客户端程序
 *
 * @author Bullet
 * @time 2017-06-03 19:20
 */
public class Client {

  public static void main(String[] args) {
    int requestNum = 1;

    for (int i = 0; i < requestNum; i++) {
      new Thread(new SendRequestRunnable()).start();
    }
  }

  private static class SendRequestRunnable implements Runnable {

    @Override
    public void run() {
      Socket cs = new Socket();
      try {
        cs.connect(new InetSocketAddress(8888));
        OutputStream out = cs.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        bw.write("GET / HTTP/1.1\r\n王子杰");
        bw.flush();
        bw.close();
        cs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
