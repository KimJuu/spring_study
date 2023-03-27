package servlet;

import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class Hello implements SimpleServlet {
  private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    logger.info("servlet/Hello CALL");
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
}
