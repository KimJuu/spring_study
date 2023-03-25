package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

public class RequestLine {

  private static final String SPACE_DELIMITER = " ";
  private static final int URL_INDEX = 1;
  private static final String QUESTION_DELIMITER = "\\?";
  private static final int PATH_INDEX = 0;
  private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

  private String path;

  private RequestLine(String path) {
    this.path = path;
  }

  public static RequestLine parse(String requestLine) {
    logger.info("RequestLine : " + requestLine);
    String[] requestLineToken = requestLine.split(SPACE_DELIMITER);
    String[] urlToken = requestLineToken[URL_INDEX].split(QUESTION_DELIMITER);

    return new RequestLine(urlToken[PATH_INDEX]);
  }

  public String getPath() {
    return path;
  }
}
