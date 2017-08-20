import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 描述Http请求
 *
 * @author Bullet
 * @time 2017-05-30 23:21
 */

/**
 * Http请求信息
 */
public class Request {

  public String version;                                // 协议版本号
  public String path;                                   // 相对于Host的路径
  public String url;                                    // 绝对的URL地址
  public Head head = new Head();                        // 请求头信息
  public Method method;                                 // 请求方法
  public Optional<Body> body = Optional.empty();        // 请求体信息，可能不存在

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Request request = (Request) o;

    if (version != null ? !version.equals(request.version) : request.version != null) {
      return false;
    }
    if (path != null ? !path.equals(request.path) : request.path != null) {
      return false;
    }
    if (url != null ? !url.equals(request.url) : request.url != null) {
      return false;
    }
    if (head != null ? !head.equals(request.head) : request.head != null) {
      return false;
    }
    if (method != request.method) {
      return false;
    }
    return body != null ? body.equals(request.body) : request.body == null;
  }

  @Override
  public int hashCode() {
    int result = version != null ? version.hashCode() : 0;
    result = 31 * result + (path != null ? path.hashCode() : 0);
    result = 31 * result + (url != null ? url.hashCode() : 0);
    result = 31 * result + (head != null ? head.hashCode() : 0);
    result = 31 * result + (method != null ? method.hashCode() : 0);
    result = 31 * result + (body != null ? body.hashCode() : 0);
    return result;
  }

  // Http请求头
  public class Head {
    Map<String, String> headers = new HashMap<>();

    @Override
    public String toString() {
      return "Head{" +
          "headers=" + headers.toString() +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Head head = (Head) o;

      return headers != null ? headers.equals(head.headers) : head.headers == null;
    }

    @Override
    public int hashCode() {
      return headers != null ? headers.hashCode() : 0;
    }
  }

  // Http请求方法
  public enum Method {
    GET, POST, HEAD, PUT, DELETE, OPTIONS, CONNECT, TRACE, PATCH;

    /**
     * 判断该方法是否允许请求体存在
     * @return 允许返回true， 否则返回false
     */
    public boolean isAllowBody() {
      return (this == Method.POST || this == Method.PUT || this == Method.PATCH);
    }

    /**
     * 依据给定的一个字符串生成方法对象
     * @param name 给定的表示HTTP请求方法的字符串
     * @return 如果方法存在，返回该对象，如果不存在，返回false
     */
    public static Method getMethodByName(String name) {
      for (Method m : Method.values()) {
        if (m.name().equals(name))
          return m;
      }
      return null;
    }

    @Override
    public String toString() {
      return "Method{ "+ this.name() + "}";
    }
  }

  // Http请求体，仅在请求方法为POST,PUT,PATCH时存在
  public class Body {
    public final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    public final String CONTENT_TYPE_MULTI_FORM = "multipart/form-data";

    public String contentType = null;                   // Http请求主体的文本类型
    public Optional<String> boundary = null;            // Http多表单请求的分割边界
    public Optional<String> charset;                    // Url编码提交时的编码格式
    public String requestBody = null;                   // 保存原始的数据

    @Override
    public String toString() {
      return "Body{" +
          "CONTENT_TYPE_URLENCODED='" + CONTENT_TYPE_URLENCODED + '\'' +
          ", CONTENT_TYPE_MULTI_FORM='" + CONTENT_TYPE_MULTI_FORM + '\'' +
          ", contentType='" + contentType + '\'' +
          ", boundary=" + boundary +
          ", charset=" + charset +
          ", requestBody='" + requestBody + '\'' +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Body body = (Body) o;

      if (CONTENT_TYPE_URLENCODED != null ? !CONTENT_TYPE_URLENCODED
          .equals(body.CONTENT_TYPE_URLENCODED) : body.CONTENT_TYPE_URLENCODED != null) {
        return false;
      }
      if (CONTENT_TYPE_MULTI_FORM != null ? !CONTENT_TYPE_MULTI_FORM
          .equals(body.CONTENT_TYPE_MULTI_FORM) : body.CONTENT_TYPE_MULTI_FORM != null) {
        return false;
      }
      if (contentType != null ? !contentType.equals(body.contentType) : body.contentType != null) {
        return false;
      }
      if (boundary != null ? !boundary.equals(body.boundary) : body.boundary != null) {
        return false;
      }
      if (charset != null ? !charset.equals(body.charset) : body.charset != null) {
        return false;
      }
      return requestBody != null ? requestBody.equals(body.requestBody) : body.requestBody == null;
    }

    @Override
    public int hashCode() {
      int result = CONTENT_TYPE_URLENCODED != null ? CONTENT_TYPE_URLENCODED.hashCode() : 0;
      result =
          31 * result + (CONTENT_TYPE_MULTI_FORM != null ? CONTENT_TYPE_MULTI_FORM.hashCode() : 0);
      result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
      result = 31 * result + (boundary != null ? boundary.hashCode() : 0);
      result = 31 * result + (charset != null ? charset.hashCode() : 0);
      result = 31 * result + (requestBody != null ? requestBody.hashCode() : 0);
      return result;
    }
  }

  @Override
  public String toString() {
    return "Request{" +
        "version='" + version + '\'' +
        ", path='" + path + '\'' +
        ", url='" + url + '\'' +
        ", head=" + head +
        ", method=" + method +
        ", body=" + body +
        '}';
  }
}
