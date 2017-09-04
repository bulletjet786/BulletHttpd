import java.util.HashMap;
import java.util.Map;

/**
 * Cookie管理器，暂时由每个Request各持有一个
 *
 * @author Bullet
 * @time 2017-08-29 16:30
 */
public class CookieManager {

  public Map<String, Cookie> cookies = new HashMap<>();

  /**
   * 从CookieManager中得到一个指定的key的Cookie
   *
   * @param key 要得到的Cookie的key
   * @return 存在返回该Cookie，失败返回null
   */
  public Cookie get(String key) {
    return cookies.get(key);
  }


  /**
   * 向CookieManager中添加一个新的cookie，如果已存在则替换
   *
   * @param cookie 要添加的cookie
   */
  public void set(Cookie cookie) {
    cookies.put(cookie.key, cookie);
  }

  /**
   * 从CookieManager中删除一个指定的key的cookie
   *
   * @param key 要删除的cookie的key
   */
  public void delete(String key) {
    cookies.remove(key);
  }


}
