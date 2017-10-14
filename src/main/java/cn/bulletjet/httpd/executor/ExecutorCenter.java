package cn.bulletjet.httpd.executor;

import cn.bulletjet.httpd.Httpd;
import cn.bulletjet.httpd.controller.Controller;
import cn.bulletjet.httpd.controller.StaticFileController;
import cn.bulletjet.httpd.exception.NotSupportException;
import cn.bulletjet.httpd.fileupload.UploadFileManager;
import cn.bulletjet.httpd.request.Request;
import cn.bulletjet.httpd.request.RequestResolver;
import cn.bulletjet.httpd.response.Response;
import cn.bulletjet.httpd.response.ResponseGenerator;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 所有连接的执行中心
 *
 * @author Bullet
 * @time 2017-09-06 22:26
 */
public class ExecutorCenter {

  private int nThreads = 16;
  private MainRunnable mainRunnable = new MainRunnable();
  private Thread mainThread = new Thread(mainRunnable);
  private ExecutorService workerService = Executors.newFixedThreadPool(nThreads);
  private int port;
  private InetAddress address;
  private UploadFileManager uploadFileManager = null;
  private Controller controller;
  private boolean mainStoped = false;

  public ExecutorCenter(Httpd.Context context) {
    this.nThreads = context.nWorkersThread;
    this.port = context.port;
    this.address = context.address;
    this.uploadFileManager = UploadFileManager.createFileManager(context.uploadPath);
    this.controller = context.controller;
  }

  public ExecutorCenter(int port, InetAddress address) {
    this.port = port;
    this.address = address;
  }

  // 执行工作
  public void run() {
    mainThread.start();
  }

  public void shutdown() {
    mainStoped = true;
    workerService.shutdown();
    while (workerService.isShutdown()) {
      uploadFileManager.deleteAll();
    }
  }

  // 主线程
  public class MainRunnable implements Runnable {

    @Override
    public void run() {
      try {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(address, port));
        while (!mainStoped) {
          Socket cs = ss.accept();
          workerService.submit(new WorkerRunnable(cs, uploadFileManager, controller));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // 工作线程
  public class WorkerRunnable implements Runnable {

    private Socket socket = null;
    private Controller controller = new StaticFileController();
    private UploadFileManager uploadFileManager;

    public WorkerRunnable(Socket socket, UploadFileManager uploadFileManager,
        Controller controller) {
      this.uploadFileManager = uploadFileManager;
      this.controller = controller;
      this.socket = socket;
    }

    @Override
    public void run() {
      InputStream in = null;
      OutputStream out = null;
      try {
        in = socket.getInputStream();
        out = socket.getOutputStream();
        int count = 0;
        while (!socket.isInputShutdown() && !socket.isOutputShutdown() && !socket.isClosed()) {
          System.out.println("New Request!");
          RequestResolver rr = new RequestResolver();
          Request request = rr.resolver(in, this.uploadFileManager.createRequestFileManager());
          System.out.println("Request..." + ++count + "     " + request.path);
          System.out.println(socket.getPort());
          Response response = controller.serve(request);
          request.uploadFileManager.clear();
          uploadFileManager
              .delete(request.uploadFileManager.getBasePath().getFileName().toString());
          System.out.println("Response..." + count);
          ResponseGenerator responseGenerator = new ResponseGenerator();
          out.write(responseGenerator.getResponseBytes(response));
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (NotSupportException e) {
        e.printStackTrace();
      }
    }
  }


}
