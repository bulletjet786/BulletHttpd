package cn.bulletjet;

import cn.bulletjet.httpd.controller.Controller;
import cn.bulletjet.httpd.controller.StaticFileController;
import cn.bulletjet.httpd.request.Request;
import cn.bulletjet.httpd.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * upload file save
 *
 * @author Bullet
 * @time 2017-10-14 15:35
 */
public class SaveFileController implements Controller {

  @Override
  public Response serve(Request request) {
    if (request.path.equals("/postSubmit.html")) {
      System.out.println("Enter...");
      String filename = request.uploadFileManager.getOriginalFilename("file");
      System.out.println(filename);
      String password = request.paras.get("password");
      System.out.println(password);
      try {
        Files.copy(Paths.get(request.uploadFileManager.getFilePath("file")),
            Paths.get("./src/main/resources/my/we_are_together.jpg"));
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Failure..");
      }
      System.out.println("Success");
      return new StaticFileController().serve(request);
    }
    return new StaticFileController().serve(request);

  }
}
