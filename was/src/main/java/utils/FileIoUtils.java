package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtils {
  private static Logger logger = LoggerFactory.getLogger(FileIoUtils.class);
  public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
    try {
      logger.info("filePath : " + filePath);
      InputStream resourceAsStream = FileIoUtils.class.getResourceAsStream(filePath);
      return resourceAsStream.readAllBytes();
    }catch (Exception e){


    }
    return null;
  }
}