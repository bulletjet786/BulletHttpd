package cn.bulletjet;

import cn.bulletjet.httpd.Httpd;
import cn.bulletjet.httpd.exception.NotSupportException;
import java.io.IOException;

/**
 * main
 *
 * @author Bullet
 * @time 2017-10-12 15:07
 */
public class Main {

  public static void main(String[] args) throws IOException, NotSupportException {
    System.out.println("Start...");
    Httpd httpd = new Httpd.Builder().setPort(8080).setController(new SaveFileController()).build();
    if (httpd != null) {
      httpd.run();
    }
    System.out.println("Run...");
  }
}
