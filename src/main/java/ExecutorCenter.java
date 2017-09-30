import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 所有连接的执行中心
 *
 * @author Bullet
 * @time 2017-09-06 22:26
 */
public class ExecutorCenter {

  private int nThreads = 16;
  private MainRunnable mainRunnable = new MainRunnable();
  private Thread mainThread = new Thread(mainRunnable);
  private ExecutorService workerService = Executors.newFixedThreadPool(nThreads);

  // 主线程
  public class MainRunnable implements Runnable {

    private int port;
    private InetAddress address;

    public MainRunnable() {
      this.port = 8080;
      address = InetAddress.getLoopbackAddress();
    }

    public MainRunnable(int port) {
      super();
      this.port = port;
    }

    public MainRunnable(int port, InetAddress address) {
      super();
      this.port = port;
      this.address = address;
    }

    @Override
    public void run() {
      try {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(port));
        while (true) {
          Socket cs = ss.accept();
          workerService.submit(new WorkerRunnable(cs));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }


  }

  // 工作线程
  public class WorkerRunnable implements Runnable {

    private Socket socket = null;
    private OnServeListener onServeListener = new DefaultOnServeListener();

    public WorkerRunnable(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      InputStream in = null;
      OutputStream out = null;
      try {
        in = socket.getInputStream();
        out = socket.getOutputStream();
        int count = 0;
        while (!socket.isInputShutdown() && !socket.isOutputShutdown() && !socket.isClosed()) {
          RequestResolver rr = new RequestResolver();
          Request request = rr.resolver(in);
          System.out.println("Request..." + ++count + "     " + request.path);
          Response response = onServeListener.serve(request);
          System.out.println("Response..." + count);
          out.write(response.genResponse().getBytes());
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (NotSupportException e) {
        e.printStackTrace();
      }
    }
  }

  // 执行工作
  public void run() {
    mainThread.start();
  }


}
