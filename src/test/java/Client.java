import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * 用于测试客户端程序
 *
 * @author Bullet
 * @time 2017-06-03 19:20
 */
public class Client {

  public static String requestText =
      "GET /image.html?id=5&name=%e7%96%be%e9%a3%8e%e5%89%91%e8%b1%aa HTTP/1.1\r\n"
          + "Host: www.baidu.com\r\n"
          + "Connection: keep-alive\r\n"
          + "Upgrade-Insecure-Requests: 1\r\n"
          + "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36\r\n"
          + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n"
          + "DNT: 1\r\n"
          + "Accept-Encoding: gzip, deflate, sdch, br\r\n"
          + "Accept-Language: zh-CN,zh;q=0.8\r\n"
          + "cn.bulletjet.httpd.CookieCookie: BAIDUID=FE8DCF7A6F252B926ED7309BABAF1F3E:FG=1; BIDUPSID=FE8DCF7A6F252B926ED7309BABAF1F3E; PSTM=1476625749; BDUSS=DNMMVZMZmxMYXhoaXpQNnJsNkN5ZnlWcTN2QjI2dm1qNExoTnRRU35QUmJGVXBZSVFBQUFBJCQAAAAAAAAAAAEAAAA7QFgqwvG5x9bSu-oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFuIIlhbiCJYS; BAIDUCUID=++; __cfduid=d40eac4721142cd94f4151592e210eddb1494867352; BD_UPN=12314353; B64_BOT=1; cflag=15%3A3; pgv_pvi=1049399296; BDRCVFR[WLjnenwr-vY]=HE6Krexxi4nmMNBIyVEQhPEUf; BD_HOME=1; BD_CK_SAM=1; PSINO=7; H_PS_PSSID=1442_21091_17001_20698_20930; sug=0; sugstore=0; ORIGIN=0; bdime=0; BDSVRTM=0\r\n"
          + "\r\n";

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
        cs.connect(new InetSocketAddress(8080));
        OutputStream out = cs.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        bw.write(requestText);
        BufferedReader bin = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        bw.flush();
        char[] chars = new char[4096];
        int length;
        while ((length = bin.read(chars)) != -1) {
          System.out.print(Arrays.copyOfRange(chars, 0, length));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
