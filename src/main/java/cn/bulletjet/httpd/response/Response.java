package cn.bulletjet.httpd.response;

import cn.bulletjet.httpd.cookie.CookieManager;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP响应报文
 *
 * @author Bullet
 * @time 2017-05-31 13:05
 */
public class Response {

  public static final String SEPARATOR = "\r\n";
  private static final Map<Integer, String> statusAndPhrases = new HashMap();

  static {
    statusAndPhrases.put(101, "101 Switching Protocols");
    statusAndPhrases.put(200, "200 OK");
    statusAndPhrases.put(201, "201 Created");
    statusAndPhrases.put(202, "202 Accepted");
    statusAndPhrases.put(204, "204 No Content");
    statusAndPhrases.put(206, "206 Partial Content");
    statusAndPhrases.put(207, "207 Multi-Status");
    statusAndPhrases.put(301, "301 Moved Permanently");
    statusAndPhrases.put(303, "303 See Other");
    statusAndPhrases.put(304, "304 Not Modified");
    statusAndPhrases.put(400, "400 Bad Request");
    statusAndPhrases.put(401, "401 Unauthorized");
    statusAndPhrases.put(403, "403 Forbidden");
    statusAndPhrases.put(404, "404 Not Found");
    statusAndPhrases.put(405, "405 Method Not Allowed");
    statusAndPhrases.put(406, "406 Not Acceptable");
    statusAndPhrases.put(408, "408 Request Timeout");
    statusAndPhrases.put(409, "409 Conflict");
    statusAndPhrases.put(416, "416 Requested Range Not Satisfiable");
    statusAndPhrases.put(500, "500 Internal Server Error");
    statusAndPhrases.put(501, "501 Not Implemented");
    statusAndPhrases.put(505, "505 HTTP Version Not Supported");
  }

  public final Map<String, String> headers = new HashMap<>();
  public CookieManager cookieManager = new CookieManager();
  public int status = 200;
  private String version = "HTTP/1.0";
  public byte[] body;

  public String getVersion() {
    return version;
  }

  public static String getStatusAndPhrase(int status) {
    String statusAndPhrase = statusAndPhrases.get(status);
    if (statusAndPhrase == null) {
      return statusAndPhrases.get(501);
    } else {
      return statusAndPhrase;
    }
  }

  // 填充默认的响应头信息
  {
    headers.put("Content-Type", "text/html; charset=utf-8");
  }

}
