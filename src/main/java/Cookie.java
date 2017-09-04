import java.time.LocalDateTime;

/**
 * Cookie实体类
 *
 * @author Bullet
 * @time 2017-08-29 16:16
 */
public class Cookie {

  public String key;
  public String value;
  public LocalDateTime expire;             // 过期时间

  public Cookie(String key, String value, LocalDateTime expire) {
    this.key = key;
    this.value = value;
    this.expire = expire;
  }

  public Cookie(String key, String value) {
    this.key = key;
    this.value = value;
    // TODO： 默认过期时间暂定为30天
    this.expire = LocalDateTime.now().plusDays(30);
  }

}
