package servlet;

import http.HttpRequest;
import http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrentDateServlet implements SimpleServlet{
  private static Logger logger = LoggerFactory.getLogger(CurrentDateServlet.class);

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    logger.info("servlet/CurrentDateServlet CALL");
    logger.info("현재 시각은 " + currentDate()  + "입니다.");

    if (httpRequest.isPost()) {
      doPost(httpRequest, httpResponse);
      return;
    }
    doGet(httpRequest, httpResponse);
  }

  @Override
  public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest);
  }

  @Override
  public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest);
  }

  public static String currentDate(){
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
