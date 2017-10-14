package cn.bulletjet.httpd.controller;

import cn.bulletjet.httpd.request.Request;
import cn.bulletjet.httpd.response.Response;

/**
 * 用户自定义的服务器方法监听器
 *
 * @author Bullet
 * @time 2017-09-07 2:02
 */
public interface Controller {

  Response serve(Request request);

}
