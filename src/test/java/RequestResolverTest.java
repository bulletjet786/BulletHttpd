import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
/**
 * 用于测试RequestResolver类
 *
 * @author Bullet
 * @time 2017-08-20 8:31
 */
public class RequestResolverTest {

  private RequestResolver resolver = new RequestResolver();

  @Test
  public void testResolver() {

  }

  @Test
  public void testResolverGet() throws IOException {
    Request expect = new Request();
    expect.method = Request.Method.GET;
    expect.path = "/image.html?id=5&name=疾风剑豪";
    expect.url = "www.baidu.com" + expect.path;
    expect.version = "HTTP/1.1";
    Map<String, String> map = new HashMap<>();
    map.put("Host".toLowerCase(), "www.baidu.com");
    map.put("Connection".toLowerCase(), "keep-alive");
    map.put("Upgrade-Insecure-Requests".toLowerCase(), "1");
    map.put("User-Agent".toLowerCase(), "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    map.put("Accept".toLowerCase(), "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    map.put("DNT".toLowerCase(), "1");
    map.put("Accept-Encoding".toLowerCase(), "gzip, deflate, sdch, br");
    map.put("Accept-Language".toLowerCase(), "zh-CN,zh;q=0.8");
    map.put("Cookie".toLowerCase(), "BAIDUID=FE8DCF7A6F252B926ED7309BABAF1F3E:FG=1; BIDUPSID=FE8DCF7A6F252B926ED7309BABAF1F3E; PSTM=1476625749; BDUSS=DNMMVZMZmxMYXhoaXpQNnJsNkN5ZnlWcTN2QjI2dm1qNExoTnRRU35QUmJGVXBZSVFBQUFBJCQAAAAAAAAAAAEAAAA7QFgqwvG5x9bSu-oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFuIIlhbiCJYS; BAIDUCUID=++; __cfduid=d40eac4721142cd94f4151592e210eddb1494867352; BD_UPN=12314353; B64_BOT=1; cflag=15%3A3; pgv_pvi=1049399296; BDRCVFR[WLjnenwr-vY]=HE6Krexxi4nmMNBIyVEQhPEUf; BD_HOME=1; BD_CK_SAM=1; PSINO=7; H_PS_PSSID=1442_21091_17001_20698_20930; sug=0; sugstore=0; ORIGIN=0; bdime=0; BDSVRTM=0");
    expect.head.headers = map;
    FileInputStream fin = new FileInputStream(new File(".").getAbsolutePath() + "/src/main/resources/get.txt");
    RequestResolver rr = new RequestResolver();
    Request result = rr.resolver(fin);
    System.out.println(expect);
    System.out.println(result);
    fin.close();
    Assert.assertEquals(expect.hashCode(), result.hashCode());
  }

  @Test
  public void testResolverPost() throws IOException {
    Request expect = new Request();
    expect.method = Request.Method.POST;
    expect.path = "/api/feed/index.html";
    expect.url = "www.myhost.com" + expect.path;
    expect.version = "HTTP/1.1";
    Map<String, String> map = new HashMap<>();
    map.put("Accept-Encoding".toLowerCase(), "gzip");
    map.put("Content-Length".toLowerCase(), "225873");
    map.put("Host".toLowerCase(), "www.myhost.com");
    map.put("Connection".toLowerCase(), "Keep-Alive");
    map.put("Content-Type".toLowerCase(), "application/x-www-form-urlencoded;charset=utf-8");
    expect.head.headers = map;
    expect.body = Optional.of(expect.new Body());
    expect.body.get().charset = Optional.of("utf-8");
    expect.body.get().contentType = expect.body.get().CONTENT_TYPE_URLENCODED;
    expect.body.get().requestBody = "id=3&name=百里守约";
    FileInputStream fin = new FileInputStream(new File(".").getAbsolutePath() + "/src/main/resources/post3.txt");
    RequestResolver rr = new RequestResolver();
    Request request = rr.resolver(fin);
    System.out.println(request);
    System.out.println(expect);
    Assert.assertEquals(expect.toString(), request.toString());

  }

}