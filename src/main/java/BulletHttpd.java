import java.io.IOException;

/**
 * HTTP服务器核心代码
 *
 * @author Bullet
 * @time 2017-05-31 12:03
 */
public class BulletHttpd {


  public static void main(String[] args) throws IOException, NotSupportException {
    System.out.println("Start...");
    ExecutorCenter executorCenter = new ExecutorCenter();
    executorCenter.run();
    System.out.println("Run...");
  }


}
