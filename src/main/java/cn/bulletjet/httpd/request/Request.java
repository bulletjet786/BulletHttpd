package cn.bulletjet.httpd.request;

import cn.bulletjet.httpd.cookie.CookieManager;
import cn.bulletjet.httpd.fileupload.RequestFileManager;
import cn.bulletjet.httpd.utils.Utils;
import java.util.Arrays;
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
  public Map<String, String> headers = new HashMap<>(); // 请求头信息
  public Method method;                                 // 请求方法
  public Optional<Body> body = Optional.empty();        // 请求体信息，可能不存在
  public String queryString;                            // 请求行中的<query>(?)和<frag>(#)字段
  public Map<String, String> paras = new HashMap<>();   // 用来保存请求头中queryString和请求体中的参数信息
  public RequestFileManager uploadFileManager = null;   // 保存所有的上传文件
  public CookieManager cookies = new CookieManager();   // 保存所有的Cookie

  public String toJsonString() {
    return Utils.toJsonString(this);
  }

  public Request(RequestFileManager requestFileManager) {
    this.uploadFileManager = requestFileManager;
  }

  @Override
  public String toString() {
    return "Request{"
        + "version='" + version + '\''
        + ", path='" + path + '\''
        + ", url='" + url + '\''
        + ", headers=" + headers
        + ", method=" + method
        + ", body=" + body
        + ", queryString='" + queryString + '\''
        + ", paras=" + paras
        + ", uploadFileManager=" + uploadFileManager
        + ", cookies=" + cookies
        + '}';
  }

  // Http请求方法
  public enum Method {
    GET, POST, HEAD, PUT, DELETE, OPTIONS, CONNECT, TRACE, PATCH;

    /**
     * 依据给定的一个字符串生成方法对象
     *
     * @param name 给定的表示HTTP请求方法的字符串
     * @return 如果方法存在，返回该对象，如果不存在，返回false
     */
    public static Method getMethodByName(String name) {
      for (Method m : Method.values()) {
        if (m.name().equals(name)) {
          return m;
        }
      }
      return null;
    }

    /**
     * 判断该方法是否允许请求体存在
     *
     * @return 允许返回true， 否则返回false
     */
    public boolean isSupportBody() {
      return (this == Method.POST || this == Method.PUT || this == Method.PATCH);
    }

    @Override
    public String toString() {
      return "Method{ " + this.name() + "}";
    }
  }

  // Http请求体，仅在请求方法为POST,PUT,PATCH时存在
  public class Body {

    public static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_MULTI_FORM = "multipart/form-data";

    public String contentType = null;                               // Http请求主体的文本类型
    public Optional<String> boundary = Optional.empty();            // Http多表单请求的分割边界
    public Optional<String> charset = Optional.empty();             // Url编码提交时的编码格式
    public byte[] requestBody = null;                               // 保存原始的数据

    public String toJsonString() {
      return Utils.toJsonString(this);
    }

    @Override
    public String toString() {
      return "Body{"
          + "contentType='" + contentType + '\''
          + ", boundary=" + boundary
          + ", charset=" + charset
          + ", requestBody=" + Arrays.toString(requestBody)
          + '}';
    }
  }
}
