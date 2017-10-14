package cn.bulletjet.httpd.response;

import cn.bulletjet.httpd.cookie.Cookie;
import cn.bulletjet.httpd.request.Request;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;

/**
 * 用于生成Response
 *
 * @author Bullet
 * @time 2017-05-31 13:45
 */
public class ResponseGenerator {

  private String webRoot = new File(".") + "/src/main/resources/static";

  public Response genResponseByRequest(Request request) throws IOException {
    String path = webRoot + request.path;
    if (new File(path).exists()) {
      Response response = new Response();
      response.body = readFile(path).getBytes("utf-8");
      return response;
    } else {
      return null;
    }
  }


  public String readFile(String path) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
    StringBuilder sb = new StringBuilder();
    String buff;
    while ((buff = br.readLine()) != null) {
      sb.append(buff + Response.SEPARATOR);
    }
    return sb.toString();
  }

  /**
   * 生成响应头的字符串表示
   *
   * @return HTTP响应头
   */
  private String genHead(Response response) {
    StringBuilder sb = new StringBuilder();
    for (Entry<String, String> entry : response.headers.entrySet()) {
      sb.append(entry.getKey() + ": " + entry.getValue() + Response.SEPARATOR);
    }
    for (Cookie cookie : response.cookieManager.values()) {
      sb.append("Set-Cookie: " + cookie.toResponseFormat() + Response.SEPARATOR);
    }
    sb.append(Response.SEPARATOR);
    return sb.toString();
  }

  public byte[] getResponseBytes(Response response) {
    response.headers.put("Content-Length", String.valueOf(response.body.length));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // 写入请求行
    try {
      byte[] lineBytes = (response.getVersion() + Response.SEPARATOR + Response
          .getStatusAndPhrase(response.status)).getBytes("ascii");
      byte[] headBytes = genHead(response).getBytes("ascii");
      byte[] bodyBytes = response.body;
      baos.write(lineBytes);
      baos.write(headBytes);
      baos.write(bodyBytes);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return baos.toByteArray();
  }

}
