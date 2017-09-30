import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 用于生成Response
 *
 * @author Bullet
 * @time 2017-05-31 13:45
 */
public class ResponseGenerator {

  private Response response = new Response();
  private String webRoot = new File(".") + "/src/main/resources/static";

  public Response genResponseByRequest(Request request) throws IOException {
    String path = webRoot + request.path;
    if (new File(path).exists()) {
      Response response = new Response();
      response.body.text = readFile(path);
      return response;
    }
    return genResponseOf404();
  }

  public Response genResponseOf404() throws IOException {
    Response response = new Response();
    response.line.status = 404;
    response.line.phrase = "Not Found";
    response.head.headers.put("Content-Length", "" + "404".getBytes().length);
    response.body.text = "404";
    return response;
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
}
