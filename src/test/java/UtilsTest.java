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
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'B', 'C', 'D'}));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'C', 'D'}));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'E', 'A'}));
    Assert.assertEquals(-2, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, null));
    Assert.assertEquals(0, Utils.findFirst(new Byte[]{'A', 'B', 'C', 'D'}, new Byte[]{'A'}, 3));
    Assert.assertEquals(2, Utils.findFirst(new Byte[]{'A', 'B', 'C', 'D'}, new Byte[]{'C'}, 3));
    Assert.assertEquals(-1, Utils.findFirst(new Byte[]{'A', 'B', 'C', 'D'}, new Byte[]{'D'}, 3));
    Assert.assertEquals(1, Utils.findFirst(new Byte[]{'A', 'B', 'C'}, new Byte[]{'B', 'C'}));
    Assert.assertEquals(1, Utils.findFirst("ABCBC".getBytes(), "BC".getBytes()));
    Assert.assertEquals(3,
        Utils.findFirst(new byte[]{'A', 'B', 'C', 013, 010}, new byte[]{013, 010}));
    Assert.assertEquals(1, Utils.findFirst("ABC".getBytes(), "BC".getBytes()));
  }

  @Test
  public void testFind() {
    Assert.assertEquals(0, Utils.find("ABC".getBytes(), "A".getBytes(), 1));
    Assert.assertEquals(-1, Utils.find("ABC".getBytes(), "b".getBytes(), 1));
    Assert.assertEquals(1, Utils.find("ABCBC".getBytes(), "BC".getBytes(), 1));
    Assert.assertEquals(3, Utils.find("ABCBC".getBytes(), "BC".getBytes(), 2));
    Assert
        .assertEquals(3, Utils.find(new byte[]{'A', 'B', 'C', 013, 010}, new byte[]{013, 010}, 1));
    Assert.assertEquals(5,
        Utils.find(new byte[]{'A', 'B', 'C', 013, 010, 013, 010}, new byte[]{013, 010}, 2));
  }

  @Test
  public void testSplit() {
    Assert.assertEquals(new byte[][]{"".getBytes(), "BCBC".getBytes()},
        Utils.split("ABCBC".getBytes(), "A".getBytes()));
    Assert.assertEquals(new byte[][]{"A".getBytes(), "C".getBytes(), "C".getBytes()},
        Utils.split("ABCBC".getBytes(), "B".getBytes()));
    Assert.assertEquals(new byte[][]{"AB".getBytes(), "B".getBytes(), "".getBytes()},
        Utils.split("ABCBC".getBytes(), "C".getBytes()));
    Assert.assertEquals(new byte[][]{"A".getBytes(), "".getBytes(), "".getBytes()},
        Utils.split("ABCBC".getBytes(), "BC".getBytes()));
    Assert.assertEquals(new byte[][]{"A".getBytes(), "BC".getBytes()},
        Utils.split("ABBBC".getBytes(), "BB".getBytes()));
    Assert.assertEquals(new byte[][]{"ABC".getBytes()},
        Utils.split("ABC".getBytes(), "D".getBytes()));

  }

}
