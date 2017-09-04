import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用fileManagerRoot来建立所有文件管理器的根目录
 * 根目录必须是个不存在的路径已支持创建
 * 每一个文件管理器会负责根目录下的一个子目录，目录名从0递增
 * 每个文件管理器管理自己目录下的文件，真实文件名从0递增
 *
 * @author Bullet
 * @time 2017-08-29 11:21
 */
public class FileManager {

  // 创建FileManager相对目录计数器，用来生成相对目录名
  private static long fileManagerCount = 0;
  // 全部FileManager所在绝对目录
  private static String fileManagerRoot = new File(".") + "/src/main/resources/tmp_file";

  // 创建全部fileManagerRoot根目录
  static {
    try {
      if (Files.exists(Paths.get(fileManagerRoot))) {
        System.out.println("路径已存在！");
      }
      Files.createDirectory(Paths.get(fileManagerRoot));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 在当前FileManager目录中文件计数器，用来生成文件名
  private long fileNameCount = 0;
  // 当前FileManager创建文件的根目录
  private String fileRoot = fileManagerRoot + "/" + fileManagerCount++;
  // 文件名到文件路径的映射，文件名(key)和真正的文件名(value中的文件名)一般情况下都是不相同的
  private Map<String, String> filePathMap = new HashMap<>();

  // 创建单个FileManager管理的子目录
  {
    try {
      Files.createDirectory(Paths.get(fileRoot));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 创建一个FileManager，使用系统自动生成的seed作为文件名生成器的种子
   *
   * @return 创建成功的FileManager对象
   */
  public static FileManager getInstance() {
    FileManager fileManager = new FileManager();
    return fileManager;
  }


  /**
   * 将bytes写进某个路径下的文件中，使用filename可获取真正的文件路径
   *
   * @param filename 传递进来的文件名，可使用该名作为get方法的参数可获取真正的文件路径
   * @return 保存成功后的文件路径
   */
  public String save(String filename, byte[] bytes) throws IOException {
    String filePath = fileRoot + "/" + fileNameCount++;
    filePathMap.put(filename, filePath);
    OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
    out.write(bytes);
    out.close();
    return filePath;
  }

  private FileManager() {
  }

  /**
   * 删除所有的文件
   */
  public void deleteAll() {
    for (String path : filePathMap.values()) {
      new File(path).delete();
    }
    filePathMap.clear();
  }

  /**
   * 在对象使用完成后释放文件夹
   */
  @Override
  protected void finalize() throws Throwable {
    deleteAll();
    new File(fileRoot).delete();

  }

  /**
   * 使用指定的filename参数来获取文件的路径
   *
   * @param filename 用来取得保存文件的地址
   * @return 文件所在的路径
   */
  public String get(String filename) {
    return filePathMap.get(filename);
  }

  @Override
  public String toString() {
    return Utils.toJsonString(this);
  }
}
