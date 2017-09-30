import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP响应报文
 *
 * @author Bullet
 * @time 2017-05-31 13:05
 */
public class Response {

  public static Response default404Response;
  public static Response default200Response;

  // 默认的404Response
  static {
    default404Response = new Response();
    default404Response.line.status = 404;
    default404Response.line.version = "HTTP/1.1";
    default404Response.line.phrase = "Not Found";
    default404Response.body.text = "This is a White Page Of 404";
  }

  public Line line = new Line();
  public Head head = new Head();
  public Body body = new Body();
  public static final String SEPARATOR = "\r\n";

  public class Line {

    public String version = "HTTP/1.1";
    public int status = 200;
    public String phrase = "OK";

    /**
     * 生成响应行的字符串表示
     *
     * @return HTTP响应行
     */
    public String genLine() {
      return version + " " + status + " " + phrase + SEPARATOR;
    }

  }

  public class Head {

    Map<String, String> headers = new HashMap<>();

    // 填充默认的响应头信息
    {
      headers.put("Content-Type", "text/html;charset=utf-8");
    }

    /**
     * 生成响应头的字符串表示
     *
     * @return HTTP响应头
     */
    public String genHead() {
      StringBuilder sb = new StringBuilder();
      for (Entry<String, String> entry : headers.entrySet()) {
        sb.append(entry.getKey() + ": " + entry.getValue() + SEPARATOR);
      }
      sb.append(SEPARATOR);
      return sb.toString();
    }

    @Override
    public String toString() {
      return "Head{" +
          "headers=" + headers.toString() +
          '}';
    }
  }

  public class Body {

    public String text = "Hello World!";

    /**
     * 生成响应正文的字符串表示
     *
     * @return HTTP响应正文
     */
    public String genBody() {
      return text;
    }
  }

  /**
   * 生成响应的字符串表示
   *
   * @returnHTTP响应
   */
  public String genResponse() {
    this.head.headers.put("Content-Length", String.valueOf(body.text.getBytes().length));
    return this.line.genLine() + this.head.genHead() + this.body.genBody();
  }

}
