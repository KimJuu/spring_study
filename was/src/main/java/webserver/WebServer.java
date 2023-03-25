package webserver;

import config.Config;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import utils.FileIoUtils;

public class WebServer {

  private static final Logger logger = LoggerFactory.getLogger(WebServer.class);


  public static void main(String args[]) throws Exception {
    // 설정파일에서 port 정보 추출
    int PORT = Config.getPort();
    logger.info("Config.getPort() : ", PORT);

    // 서버소켓을 생성한다.
    try (ServerSocket listenSocket = new ServerSocket(PORT)) {
      logger.info("Web Application Server started {} port.", PORT);
      // 클라이언트가 연결될때까지 대기한다.
      Socket connection;
      while ((connection = listenSocket.accept()) != null) {
        Thread thread = new Thread(new RequestHandler(connection));
        thread.start();
      }
    }
  }
}