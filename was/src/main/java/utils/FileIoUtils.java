package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIoUtils {
  private static Logger logger = LoggerFactory.getLogger(FileIoUtils.class);
  public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
    InputStream resourceAsStream = FileIoUtils.class.getResourceAsStream(filePath);
    return resourceAsStream.readAllBytes();
  }
}