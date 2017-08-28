import java.io.IOException;

/**
 * 验证某些想法
 *
 * @author Bullet
 * @time 2017-08-21 13:51
 */
public class Test {

  public static void main(String[] args) throws IOException {
    int[] a = new int[]{1, 2, 3};
    int[] b = new int[]{1, 2, 3};
    System.out.println(a.hashCode());
    System.out.println(b.hashCode());

  }

}
