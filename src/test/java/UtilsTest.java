import org.junit.Assert;
import org.junit.Test;

/**
 * 测试工具类
 *
 * @author Bullet
 * @time 2017-08-20 17:21
 */
public class UtilsTest {

  @Test
  public void testFindFirst() {

    Assert.assertEquals(0, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'A'}));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'b'}));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'B', 'C','D'}));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'C', 'D'}));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'E', 'A'}));
    Assert.assertEquals(-2, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, null));

  }

}
