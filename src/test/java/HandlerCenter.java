import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 处理所有的请求中心
 *
 * @author Bullet
 * @time 2017-06-03 18:49
 */
public class HandlerCenter {


  ExecutorService threadPool = Executors
      .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

  public static void main(String[] args) throws IOException {
    HandlerCenter hc = new HandlerCenter();
    hc.run();
  }

  public void run() throws IOException {
    ServerSocket ss = new ServerSocket(8888);
    System.out.println(ss.getInetAddress());
    System.out.println(ss.getLocalPort());
    System.out.println(ss.isBound());

    while (true) {
      final Socket cs = ss.accept();
      threadPool.submit(new HandleRequest(cs));
    }
  }

  private class HandleRequest implements Runnable {

    private final Socket cs;

    @Override
    public void run() {

      try {
        InputStream in = cs.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String str = br.readLine();
        System.out.println(str);

        br.close();
        cs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    public HandleRequest(final Socket cs) {
      this.cs = cs;
    }

  }


}
