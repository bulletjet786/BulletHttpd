import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * 用于解析Http请求
 *
 * @author Bullet
 * @time 2017-05-30 23:20
 */
public class RequestResolver {

  private Request request;    // 将要生成的请求对象
  public static String SEPARATOR = "\r\n";

  /**
   * 从请求行中提取请求信息
   * @param requestLine
   * @return
   */
  private boolean resolverLine(String requestLine) throws UnsupportedEncodingException {
    String [] lineArgs = requestLine.split(" ");
    request.method = Request.Method.getMethodByName(lineArgs[0]);
    request.path = URLDecoder.decode(lineArgs[1], "UTF-8");

    // TODO : 提取参数信息

    request.version = lineArgs[2];
    return true;
  }
  /**
   * 从给定的字符串中生成一个请求头
   * @param requestHead 给定的字符串
   * @return 解析成功返回true，失败返回false
   */
  private boolean resolverHead(String requestHead) {
    // 将请求信息添加进head对象中
    for (String line : requestHead.split(RequestResolver.SEPARATOR)) {
      String[] kv = line.split(": ");
      request.head.headers.put(kv[0].toLowerCase(), kv[1].trim());        // HTTP HEAD不区分大小写，此处使用小写
    }
    // 生成Url地址
    request.url = request.head.headers.get("Host".toLowerCase()) + request.path;


    // TODO: 处理cookies
    return true;
  }


  /**
   * 仅在方法支持时进行调用，用于解析请求体
   * @return 如果该请求类型实现了内置解析，解析并返回true，否则由用户自己实现解析并返回false
   */
  private boolean resolverBody(String requestBody) {
    request.body = Optional.of(request.new Body());             // 填充Body对象
    request.body.get().requestBody = requestBody;               // 将全部请求体拷贝进requestBody
    String contentTypeLine = request.head.headers.get("Content-Type".toLowerCase());
    String [] contentTypeArgs = contentTypeLine.split(";");
    request.body.get().contentType = contentTypeArgs[0].trim();

    if (request.body.get().contentType.equals(request.body.get().CONTENT_TYPE_URLENCODED)) {
      // 填充charset字段
      for (String arg: contentTypeArgs) {
        if (arg.trim().toLowerCase().startsWith("CharSet".toLowerCase())) {
          request.body.get().charset = Optional.of(arg.trim().toLowerCase().split("=")[1]);
        }
      }
      // 提取请求参数


      return true;
    } else if (request.body.get().contentType.equals(request.body.get().CONTENT_TYPE_MULTI_FORM)) {
      // 填充boundary字段
      for (String arg: contentTypeArgs) {
        if (arg.trim().toLowerCase().startsWith("Boundary".toLowerCase())) {
          request.body.get().boundary = Optional.of(arg.trim().toLowerCase().split("=")[1]);
        }
      }
        // 提取请求参数
        // TODO

      return true;
    } else {                  // 未实现的请求文本类型（Content-Type），什么都不做，由用户进行解析
      return false;
    }

  }

  public Request resolver(InputStream in) throws IOException{
    request = new Request();
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    resolverLine(br.readLine());
    if (!request.method.isAllowBody()) {          // 如果请求方法不支持请求体
      StringBuilder sb = new StringBuilder();
      String buff;
      while (!(buff = br.readLine()).equals("")) {
        sb.append(buff + SEPARATOR);
      }
      buff = sb.toString();
      resolverHead(buff);
    } else {                                      // 如果请求方法支持方法体
      StringBuilder sb = new StringBuilder();
      String buff;
      while (!(buff = br.readLine()).equals("")) {
        sb.append(buff + SEPARATOR);
      }
      buff = sb.toString();
      resolverHead(buff);
      sb = new StringBuilder();
      while ((buff = br.readLine()) != null) {
        sb.append(buff + SEPARATOR);
      }
      buff = sb.substring(0, sb.length() - 2).toString();
      resolverBody(buff);
    }

    refactorRequest();
    return request;
  }

  /**
   * 使用已经提取的Http信息重构请求对象
   * @return
   */
  private boolean refactorRequest() {

    // TODO
    return true;
  }

  public static void main(String[] args) throws IOException{

  }


}
