package cn.bulletjet.httpd.fileupload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * upload file manager
 *
 * @author Bullet
 * @time 2017-10-11 1:15
 */

/**
 * 为所有请求建立文件上传管理器，其管理着所有请求的上传文件目录
 */
public class UploadFileManager {

  private Path basePath = null;
  private int dirNameGeneratorCounter = 0;

  private UploadFileManager(String base) {
    this.basePath = Paths.get(base);
  }

  /**
   * 以base路径作为基目录，创建文件管理器
   *
   * @param base 文件保存的目录
   * @return 如果base路径不是目录或者目录不为空，返回null表示创建失败，否则返回创建的TempFileManager
   */
  public static UploadFileManager createFileManager(String base) {
    // 如果base表示的路径不是目录或者目录不为空， 返回null
    if (!Files.isDirectory(Paths.get(base)) || new File(base).list().length != 0) {
      return null;
    }
    return new UploadFileManager(base);
  }

  public synchronized RequestFileManager createRequestFileManager() throws IOException {
    createDirectory(String.valueOf(dirNameGeneratorCounter));
    return RequestFileManager
        .createFileManager(basePath + "/" + String.valueOf(dirNameGeneratorCounter++));
  }

  /**
   * 删除所有的文件
   */
  public synchronized void deleteAll() {
    for (File f : new File(basePath.toString()).listFiles()) {
      f.delete();
    }
  }

  /**
   * 如果文件存在则删除之
   */
  public synchronized void delete(String filename) {
    new File(basePath + "/" + filename).delete();
  }

  /**
   * 创建一个目录
   *
   * @param dirname 文件名
   * @return 创建成功后的目录路径，不合法则返回null
   */
  public synchronized Path createDirectory(String dirname) throws IOException {
    if (dirname.matches("[/\\\\]")) {
      return null;
    }
    return Files.createDirectory(Paths.get(basePath + "/" + dirname));
  }

}
