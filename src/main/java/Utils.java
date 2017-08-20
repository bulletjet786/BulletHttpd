/**
 * 工具类
 *
 * @author Bullet
 * @time 2017-08-20 17:08
 */
public class Utils {

  /**
   * 在bytes字节串中找到found首次出现的地方
   * @param bytes 在bytes中进行查找
   * @param found 要查找的字节串
   * @return -1表示未找到，正数表示找到的首次出现的位置,-2表示非法参数
   */
  public static int findFirst(Byte[] bytes, Byte[] found) {
    if (bytes == null || found == null)
      return -2;
    int result = -1;
    outer:
    for (int i = 0; i <= bytes.length - found.length; i++) {
      for (int j = 0; j < found.length; j++) {
        if (bytes[i+j] != found[j]) {
          continue outer;
        }
      }
      result = i;
    }
    return result;
  }


}
