import java.io.File;

/**
 * @author Bullet
 * @time 2017-08-29 11:21
 */
public class FileManager {

  public String create(String filename) {

    return new File(".").getAbsolutePath() + "/src/main/resources/temp_fileupload_1.txt";

  }


}
