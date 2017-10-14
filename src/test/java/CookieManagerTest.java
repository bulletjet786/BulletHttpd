import cn.bulletjet.httpd.cookie.Cookie;
import cn.bulletjet.httpd.cookie.CookieManager;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试CookieManager
 *
 * @author Bullet
 * @time 2017-08-29 16:57
 */
public class CookieManagerTest {

  @Test
  public void getExpireFromRFC1123String() {
    String rfc1123String = "Fri, 06 Oct 2017 13:57:37 GMT";
    ZonedDateTime dateTime = ZonedDateTime.of(2017, 10, 6, 13, 57, 37, 0, ZoneId.of("GMT"));
    Assert.assertEquals(dateTime, Cookie.getExireFromRF1123String(rfc1123String));
  }

  @Test
  public void toResponseFormatTest() {
    Cookie cookie = new Cookie("yhl", "大坏蛋",
        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"));
    Assert
        .assertEquals("yhl=大坏蛋; expires=Mon, 03 Dec 2007 09:15:30 GMT", cookie.toResponseFormat());
  }

  @Test
  public void cookieManagerCrud() {
    Cookie cookie1 = new Cookie("yhl", "大坏蛋",
        ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]"));
    Cookie cookie2 = new Cookie("wzj", "大好人");
    CookieManager cookieManager = new CookieManager();

    cookieManager.set(cookie1);
    cookieManager.set(cookie2);
    System.out.println(cookieManager.toJsonString());
    String expect1 = "{\"wzj\":{\"key\":\"wzj\",\"value\":\"大好人\"},\"yhl\":{\"key\":\"yhl\",\"value\":\"大坏蛋\",\"expire\":{\"value\":{\"dateTime\":{\"date\":{\"year\":2007,\"month\":12,\"day\":3},\"time\":{\"hour\":10,\"minute\":15,\"second\":30,\"nano\":0}},\"offset\":{\"totalSeconds\":3600},\"zone\":{\"id\":\"Europe/Paris\"}}}}}";
    Assert.assertEquals(expect1, cookieManager.toJsonString());

    cookieManager.delete("yhl");
    System.out.println(cookieManager.toJsonString());
    String expect2 = "{\"wzj\":{\"key\":\"wzj\",\"value\":\"大好人\"}}";
    Assert.assertEquals(expect2, cookieManager.toJsonString());

    Cookie cookie3 = cookieManager.get("wzj");
    System.out.println(cookie3.toJsonString());
    String expect3 = "{\"key\":\"wzj\",\"value\":\"大好人\"}";
    Assert.assertEquals(expect3, cookie3.toJsonString());

  }


}
