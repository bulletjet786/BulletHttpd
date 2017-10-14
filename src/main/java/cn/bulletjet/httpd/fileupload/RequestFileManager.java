package cn.bulletjet.httpd.fileupload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * request file manager
 *
 * @author Bullet
 * @time 2017-10-11 23:29
 */
public class RequestFileManager {

  /**
   * 参数名到原始文件名的映射, RequestFileManager将使用上传文件的参数名作为实际保存的文件名
   */
  Map<String, String> paraFilenameMap = new HashMap<>();
  private Path basePath = null;

  public Path getBasePath() {
    return basePath;
  }

  private RequestFileManager(String base) {
    this.basePath = Paths.get(base);
  }

  /**
   * 以base路径作为基目录，创建文件管理器
   *
   * @param base 文件保存的目录
   * @return 如果base路径不是目录或者目录不为空，返回null表示创建失败，否则返回创建的TempFileManager
   */
  public static RequestFileManager createFileManager(String base) {
    // 如果base表示的路径不是目录或者目录不为空， 返回null
    if (!Files.isDirectory(Paths.get(base))
        || new File(base).list().length != 0) {
      return null;
    }
    return new RequestFileManager(base);
  }

  /**
   * 使用上传文件的参数名作为实际保存的文件名，将指定的数组内容保存进去
   *
   * @param param 上传文件的参数名
   * @param filename 原始文件名
   * @param bytes 要保存内容的数组
   * @return 实际保存的文件名
   */
  public String save(String param, String filename, byte[] bytes) throws IOException {
    paraFilenameMap.put(param, filename);
    BufferedOutputStream bos = new BufferedOutputStream(
        new FileOutputStream(basePath.toString() + "/" + param));
    bos.write(bytes);
    bos.close();
    return filename;
  }


  /**
   * 得到原始文件名
   *
   * @param param Body中的请求参数名
   * @return 原始文件名，不存在为null
   */
  public String getOriginalFilename(String param) {
    return paraFilenameMap.get(param);
  }

  /**
   * 得到临时上传文件路径
   *
   * @param param 上传文件的参数名
   * @return 实际保存的文件路径，不存在为null;
   */
  public String getFilePath(String param) {
    if (paraFilenameMap.containsKey(param)) {
      return basePath.toString() + "/" + param;
    }
    return null;
  }

  public void clear() {
    for (File f : new File(basePath.toString()).listFiles()) {
      f.delete();
    }
  }
}
