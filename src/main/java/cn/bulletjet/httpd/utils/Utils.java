package cn.bulletjet.httpd.utils;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 工具类
 *
 * @author Bullet
 * @time 2017-08-20 17:08
 */
public class Utils {

  /**
   * 在bytes字节串中找到found首次出现的地方
   *
   * @param bytes 在bytes中进行查找
   * @param found 要查找的字节串
   * @return -1表示未找到，正数表示找到的首次出现的位置,-2表示非法参数
   */
  public static int findFirst(Byte[] bytes, Byte[] found) {
    if (bytes == null || found == null) {
      return -2;
    }
    outer:
    for (int i = 0; i <= bytes.length - found.length; i++) {
      for (int j = 0; j < found.length; j++) {
        if (bytes[i + j] != found[j]) {
          continue outer;
        }
      }
      return i;
    }
    return -1;
  }

  public static int findFirst(byte[] bytes, byte[] found) {
    if (bytes == null || found == null) {
      return -2;
    }
    outer:
    for (int i = 0; i <= bytes.length - found.length; i++) {
      for (int j = 0; j < found.length; j++) {
        if (bytes[i + j] != found[j]) {
          continue outer;
        }
      }
      return i;
    }
    return -1;
  }

  public static int findFirst(Byte[] bytes, Byte[] found, int length) {
    Byte[] bytesAll = new Byte[length];
    for (int i = 0; i < length; i++) {
      bytesAll[i] = bytes[i];
    }
    return findFirst(bytesAll, found);
  }

  public static int findFirst(byte[] bytes, byte[] found, int length) {
    byte[] bytesAll = new byte[length];
    for (int i = 0; i < length; i++) {
      bytesAll[i] = bytes[i];
    }
    return findFirst(bytesAll, found);
  }

  public static String toJsonString(Object obj) {
    return new Gson().toJson(obj);
  }

  /**
   * 在bytes字节串中找到found第count次出现的地方
   *
   * @param bytes 在bytes中进行查找
   * @param found 要查找的字节串
   * @param count 指定的次数,应该大于0
   * @return -1表示未找到，正数表示找到的首次出现的位置,-2表示非法参数
   */
  public static int find(byte[] bytes, byte[] found, int count) {
    if (count <= 0) {
      return -2;
    }
    if (bytes == null || found == null) {
      return -2;
    }
    int findCount = 0;
    outer:
    for (int i = 0; i <= bytes.length - found.length; i++) {
      for (int j = 0; j < found.length; j++) {
        if (bytes[i + j] != found[j]) {
          continue outer;
        }
      }
      if (++findCount == count) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 使用split将bytes切分为多个byte数组
   *
   * @param bytes 将要被切分的数组
   * @param split 切分byte数组的依据
   * @return null如果任意参数为null，否则返回被切分后的数组
   */
  public static byte[][] split(byte[] bytes, byte[] split) {
    if (bytes == null || split == null) {
      return null;
    }

    List<byte[]> resultList = new ArrayList<>();
    int count = 0;
    int indexFrom = 0;
    int indexTo;
    outer:
    for (int i = 0; i <= bytes.length - split.length; i++) {
      for (int j = 0; j < split.length; j++) {
        if (bytes[i + j] != split[j]) {
          continue outer;
        }
      }
      // 找到指定的字节数组
      indexTo = i;
      resultList.add(Arrays.copyOfRange(bytes, indexFrom, indexTo));
      i += split.length - 1;
      indexFrom = indexTo + split.length;
    }
    resultList.add(Arrays.copyOfRange(bytes, indexFrom, bytes.length));
    byte[][] result = new byte[resultList.size()][];
    for (int i = 0; i < result.length; i++) {
      result[i] = resultList.get(i);
    }
    return result;
  }

}
