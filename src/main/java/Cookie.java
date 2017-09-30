import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Cookie实体类
 *
 * @author Bullet
 * @time 2017-08-29 16:16
 */
public class Cookie {

  public String key;
  public String value;
  public Optional<LocalDateTime> expire;             // 过期时间

  public Cookie(String key, String value, LocalDateTime expire) {
    this.key = key;
    this.value = value;
    this.expire = Optional.of(expire);
  }

  public Cookie(String key, String value) {
    this.key = key;
    this.value = value;
    this.expire = null;
  }

}
