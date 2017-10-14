package cn.bulletjet.httpd;

import cn.bulletjet.httpd.controller.Controller;
import cn.bulletjet.httpd.controller.StaticFileController;
import cn.bulletjet.httpd.executor.ExecutorCenter;
import java.io.File;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Bullet
 * @time 2017-05-31 12:03
 */
public class Httpd {

  private Context context;
  private ExecutorCenter executorCenter;

  private Httpd() {
  }

  public static class Builder {

    private int port = 8080;
    private InetAddress address = InetAddress.getLoopbackAddress();
    private String uploadPath = new File(".") + "/src/main/resources/upload_file";
    private String webRoot = new File(".") + "/src/main/resources/static";
    private int nWorkersThread = 16;
    private Controller controller = new StaticFileController();

    public Builder setnWorkersThread(int nWorkersThread) {
      this.nWorkersThread = nWorkersThread;
      return this;
    }

    public Builder setPort(int port) {
      this.port = port;
      return this;
    }

    public Builder setAddress(InetAddress address) {
      this.address = address;
      return this;
    }

    public Builder setUploadPath(String uploadPath) {
      this.uploadPath = uploadPath;
      return this;
    }

    public Builder setWebRoot(String webRoot) {
      this.webRoot = webRoot;
      return this;
    }

    public Builder setController(Controller controller) {
      this.controller = controller;
      return this;
    }

    /**
     * 检查参数是否合法
     *
     * @return 合法返回true，不合法返回false
     */
    private boolean check() {
      if (!Files.exists(Paths.get(uploadPath)) || !Files.exists(Paths.get(uploadPath))) {
        return false;
      }
      if (!Files.exists(Paths.get(webRoot)) || !Files.exists(Paths.get(webRoot))) {
        return false;
      }
      return true;
    }

    /**
     * 构造一个Httpd
     *
     * @return 参数正确返回构造的对象，不正确返回null
     */
    public Httpd build() {
      if (!this.check()) {
        return null;
      }

      Httpd httpd = new Httpd();
      httpd.context = httpd.new Context();
      httpd.context.address = this.address;
      httpd.context.port = this.port;
      httpd.context.uploadPath = this.uploadPath;
      httpd.context.webRoot = this.webRoot;
      httpd.context.controller = this.controller;
      httpd.executorCenter = new ExecutorCenter(httpd.context);
      return httpd;
    }
  }

  public void run() {
    this.executorCenter.run();
  }

  public class Context {

    public int port;
    public InetAddress address;
    public String uploadPath;
    public String webRoot;
    public int nWorkersThread;
    public Controller controller;
  }

  public void shutdown() {
    executorCenter.shutdown();
  }

}
