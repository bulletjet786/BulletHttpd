/**
 * 默认的服务器控制监听器
 *
 * @author Bullet
 * @time 2017-09-07 2:07
 */
public class DefaultOnServeListener implements OnServeListener {

  @Override
  public Response serve(Request request) {

    ResponseGenerator rg = new ResponseGenerator();
    try {
      return rg.genResponseByRequest(request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
