package cn.bulletjet.httpd.cookie;

import cn.bulletjet.httpd.utils.Utils;
import java.time.ZonedDateTime;
import java.util.HashMap;

/**
 * Cookie管理器，暂时由每个Request各持有一个
 *
 * @author Bullet
 * @time 2017-08-29 16:30
 */
public class CookieManager extends HashMap<String, Cookie> {

  /**
   * 向CookieManager中添加一个新的cookie，如果已存在则替换
   *
   * @param cookie 要添加的cookie
   */
  public void set(Cookie cookie) {
    this.put(cookie.key, cookie);
  }

  public void set(String key, String value) {
    this.put(key, new Cookie(key, value));
  }

  public void set(String key, String value, ZonedDateTime dateTime) {
    this.put(key, new Cookie(key, value, dateTime));
  }

  /**
   * 从CookieManager中删除一个指定的key的cookie
   *
   * @param key 要删除的cookie的key
   */
  public void delete(String key) {
    this.remove(key);
  }

  public String toJsonString() {
    return Utils.toJsonString(this);
  }

}
