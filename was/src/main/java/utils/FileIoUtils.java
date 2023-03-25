package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

  public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
    System.out.println(
        "::::::::::::::: FileIoUtils.class.getClassLoader().getResource(filePath) :  "
            + FileIoUtils.class.getClassLoader().getResource(filePath));
    System.out.println(
        "::::::::::::::: FileIoUtils.class.getClassLoader().getResource(filePath) :  " + filePath);

    Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
    return Files.readAllBytes(path);
  }

  public static String UrlToFile(String url) {
    String fileName = "";

    switch (url) {
      case "/Hello":
        fileName = "hello.html";
        break;
      case "/service.Hello":
        fileName = "serviceHello.html";
        break;
      default:
        fileName = "index.html";
        break;
    }
    return fileName;
  }
}
