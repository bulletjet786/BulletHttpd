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

/**
 * 测试后请删除/tmp_file文件夹
 */
public class RequestResolverTest {

  private RequestResolver resolver = new RequestResolver();

  @Test
  public void testResolverGet() throws IOException, NotSupportException {
    Request expect = new Request();
    expect.method = Request.Method.GET;
    expect.path = "/image.html";
    expect.queryString = "id=5&name=疾风剑豪";
    expect.url = "www.baidu.com/image.html?id=5&name=疾风剑豪";
    expect.version = "HTTP/1.1";
    Map<String, String> headers = new HashMap<>();
    headers.put("Host".toLowerCase(), "www.baidu.com");
    headers.put("Connection".toLowerCase(), "keep-alive");
    headers.put("Upgrade-Insecure-Requests".toLowerCase(), "1");
    headers.put("User-Agent".toLowerCase(),
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    headers.put("Accept".toLowerCase(),
        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    headers.put("DNT".toLowerCase(), "1");
    headers.put("Accept-Encoding".toLowerCase(), "gzip, deflate, sdch, br");
    headers.put("Accept-Language".toLowerCase(), "zh-CN,zh;q=0.8");
    headers.put("Cookie".toLowerCase(),
        "BAIDUID=FE8DCF7A6F252B926ED7309BABAF1F3E:FG=1; BIDUPSID=FE8DCF7A6F252B926ED7309BABAF1F3E; PSTM=1476625749; BDUSS=DNMMVZMZmxMYXhoaXpQNnJsNkN5ZnlWcTN2QjI2dm1qNExoTnRRU35QUmJGVXBZSVFBQUFBJCQAAAAAAAAAAAEAAAA7QFgqwvG5x9bSu-oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFuIIlhbiCJYS; BAIDUCUID=++; __cfduid=d40eac4721142cd94f4151592e210eddb1494867352; BD_UPN=12314353; B64_BOT=1; cflag=15%3A3; pgv_pvi=1049399296; BDRCVFR[WLjnenwr-vY]=HE6Krexxi4nmMNBIyVEQhPEUf; BD_HOME=1; BD_CK_SAM=1; PSINO=7; H_PS_PSSID=1442_21091_17001_20698_20930; sug=0; sugstore=0; ORIGIN=0; bdime=0; BDSVRTM=0");
    expect.headers = headers;
    expect.paras.put("id", "5");
    expect.paras.put("name", "疾风剑豪");
    FileInputStream fin = new FileInputStream(
        new File(".").getAbsolutePath() + "/src/main/resources/get.txt");
    RequestResolver rr = new RequestResolver();
    Request result = rr.resolver(fin);
    System.out.println(expect);
    System.out.println(result);
    fin.close();
    Assert.assertEquals(expect.headers.get("content-length"),
        result.headers.get("content-length"));
    Assert.assertEquals(expect.paras.get("id"), result.paras.get("id"));
//    Assert.assertEquals(expect.toString(), result.toString());
  }

  @Test
  public void testResolverPostUrlEncode() throws IOException, NotSupportException {
    Request expect = new Request();
    expect.method = Request.Method.POST;
    expect.path = "/api/feed/index.html";
    expect.queryString = "";
    expect.url = "www.myhost.com/api/feed/index.html";
    expect.version = "HTTP/1.1";
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept-Encoding".toLowerCase(), "gzip");
    headers.put("Content-Length".toLowerCase(), "46");
    headers.put("Host".toLowerCase(), "www.myhost.com");
    headers.put("Connection".toLowerCase(), "Keep-Alive");
    headers.put("Content-Type".toLowerCase(), "application/x-www-form-urlencoded;charset=utf-8");
    expect.headers = headers;
    expect.body = Optional.of(expect.new Body());
    expect.body.get().charset = Optional.of("utf-8");
    expect.body.get().contentType = expect.body.get().CONTENT_TYPE_URLENCODED;
    expect.body.get().requestBody = "id=3&name=%E7%99%BE%E9%87%8C%E5%AE%88%E7%BA%A6".getBytes();
    expect.paras.put("id", "3");
    expect.paras.put("name", "百里守约");
    FileInputStream fin = new FileInputStream(
        new File(".").getAbsolutePath() + "/src/main/resources/post_encode.txt");
    RequestResolver rr = new RequestResolver();
    Request result = rr.resolver(fin);
    System.out.println(result);
    System.out.println(expect);
    Assert.assertArrayEquals(expect.body.get().requestBody, result.body.get().requestBody);
    Assert.assertEquals(expect.headers.get("content-length"),
        result.headers.get("content-length"));
    Assert.assertEquals(expect.paras.get("id"), result.paras.get("id"));
//    Assert.assertEquals(expect.toString(), result.toString());
  }

  @Test
  public void testResolverPostMultiForm() throws IOException, NotSupportException {
    Request expect = new Request();
    expect.method = Request.Method.POST;
    expect.path = "/api/feed/index.html";
    expect.queryString = "";
    expect.url = "www.demo.com/api/feed/index.html";
    expect.version = "HTTP/1.1";
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Length".toLowerCase(), "398");
    headers.put("Host".toLowerCase(), "www.demo.com");
    headers.put("Cache-Control".toLowerCase(), "no-cache");
    headers.put("Postman-Token".toLowerCase(), "679d816d-8757-14fd-57f2-fbc2518dddd9");
    headers.put("Content-Type".toLowerCase(),
        "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
    expect.headers = headers;
    expect.paras.put("key", "value");
    expect.paras.put("testKey", "testValue");
    expect.paras.put("imgFile",
        new File(".").getAbsolutePath() + "/src/main/resources/temp_fileupload_1.txt");
    expect.body = Optional.of(expect.new Body());
    expect.body.get().contentType = expect.body.get().CONTENT_TYPE_MULTI_FORM;
    expect.body.get().boundary = Optional.of("----WebKitFormBoundary7MA4YWxkTrZu0gW");
    expect.body.get().requestBody = ("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
        + "Content-Disposition: form-data; name=\"key\"\r\n"
        + "\r\n"
        + "value\r\n"
        + "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
        + "Content-Disposition: form-data; name=\"testKey\"\r\n"
        + "\r\n"
        + "testValue\r\n"
        + "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
        + "Content-Disposition: form-data; name=\"imgFile\"; filename=\"no-file\"\r\n"
        + "Content-Type: application/octet-stream\r\n"
        + "\r\n"
        + "111王\r\n"
        + "------WebKitFormBoundary7MA4YWxkTrZu0gW--\r\n").getBytes();
    FileInputStream fin = new FileInputStream(
        new File(".").getAbsolutePath() + "/src/main/resources/post_multi.txt");
    RequestResolver rr = new RequestResolver();
    Request result = rr.resolver(fin);
    System.out.println(result);
    System.out.println(expect);
    Assert.assertArrayEquals(expect.body.get().requestBody, result.body.get().requestBody);
    Assert.assertEquals(expect.headers.get("content-length"),
        result.headers.get("content-length"));
    Assert.assertEquals(expect.paras.get("key"), result.paras.get("key"));
    Assert.assertEquals("./src/main/resources/tmp_file/1/0", result.fileManager.get("no-file"));
//    Assert.assertEquals(expect.toString(), result.toString());
  }

}