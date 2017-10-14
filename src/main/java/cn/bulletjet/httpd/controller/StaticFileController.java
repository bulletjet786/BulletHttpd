package cn.bulletjet.httpd.controller;

import cn.bulletjet.httpd.request.Request;
import cn.bulletjet.httpd.response.Response;
import cn.bulletjet.httpd.response.ResponseGenerator;

/**
 * 默认的服务器控制监听器
 *
 * @author Bullet
 * @time 2017-09-07 2:07
 */
public class StaticFileController implements Controller {

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
