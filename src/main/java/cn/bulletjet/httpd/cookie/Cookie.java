package cn.bulletjet.httpd.cookie;

import cn.bulletjet.httpd.utils.Utils;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

/**
 * Cookie实体类
 *
 * @author Bullet
 * @time 2017-08-29 16:16
 */

/**
 * Set-Cookie: <name>=<value>[; <name>=<value>]... [; expires=<date>][; domain=<domain_name>] [;
 * path=<some_path>][; secure][; httponly] expires=<date> DAY, DD MMM YYYY HH:MM:SS GMT
 */
public class Cookie {

  public String key;
  public String value;
  public Optional<ZonedDateTime> expire;             // 过期时间
  public static DateTimeFormatter rfc1123Formatter = DateTimeFormatter
      .ofPattern("EEE, dd MMM yyyy HH:mm:ss z").withLocale(
          Locale.ENGLISH).withZone(ZoneId.of("GMT"));

  public Cookie(String key, String value, ZonedDateTime expire) {
    this.key = key;
    this.value = value;
    this.expire = Optional.of(expire);
  }

  public Cookie(String key, String value) {
    this.key = key;
    this.value = value;
    this.expire = null;
  }

  public String toResponseFormat() {
    String result = this.key + "=" + this.value;
    if (expire.isPresent()) {
      result += "; expires=" + expire.get().format(rfc1123Formatter);
    }
    return result;
  }

  public String toJsonString() {
    return Utils.toJsonString(this);
  }

  public static ZonedDateTime getExireFromRF1123String(String rfc1123String) {
    return ZonedDateTime.parse(rfc1123String, rfc1123Formatter);
  }
}
