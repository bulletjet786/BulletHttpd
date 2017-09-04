import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试FileManager
 *
 * @author Bullet
 * @time 2017-09-04 20:27
 */
public class FileManagerTest {


  private static String fileManagerRoot = new File(".") + "/src/main/resources/tmp_file";

  @Test
  public void testGetInstance() {
    FileManager.getInstance();
    Assert.assertEquals(true, Files.isDirectory(Paths.get(fileManagerRoot + "/" + 0)));
    FileManager.getInstance();
    Assert.assertEquals(true, Files.isDirectory(Paths.get(fileManagerRoot + "/" + 1)));
    FileManager.getInstance();
    Assert.assertEquals(true, Files.isDirectory(Paths.get(fileManagerRoot + "/" + 2)));
  }


}
