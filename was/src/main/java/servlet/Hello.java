package servlet;

import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hello implements SimpleServlet {
  private static final Logger logger = LoggerFactory.getLogger(Hello.class);

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    logger.info("HelloServlet Request Path : " + httpRequest.getPath());
    httpResponse.forward(httpRequest.getPath());
  }
}
