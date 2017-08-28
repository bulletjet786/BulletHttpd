import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HTTP服务器核心代码
 *
 * @author Bullet
 * @time 2017-05-31 12:03
 */
public class BulletHttpd {

  private String addr;
  private int port = 8888;


  public static void main(String[] args) throws IOException, NotSupportException {
    ServerSocket ss = new ServerSocket(8888, 20, InetAddress.getByName("localhost"));
    System.out.println(ss.getInetAddress());
    System.out.println(ss.getLocalPort());
    System.out.println(ss.isBound());
    Socket cs = ss.accept();
    InputStream in = cs.getInputStream();
    RequestResolver rr = new RequestResolver();
    Request request = rr.resolver(in);
    System.out.println("Here.............................");
    System.out.println(request);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream()));
    ResponseGenerator rg = new ResponseGenerator();
    Response response = rg.genResponseByRequest(request);
    String resp = response.genResponse();
    System.out.println(resp);
    bw.write(resp);
    bw.flush();
    bw.close();


  }


}
