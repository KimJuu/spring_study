package servlet.service;

import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.SimpleServlet;

public class Hello implements SimpleServlet {
  private static final Logger logger = LoggerFactory.getLogger(servlet.Hello.class);

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    logger.info("service.Hello Servlet Request Path : " + httpRequest.getPath());

    httpResponse.forward(httpRequest.getPath());
  }
}
