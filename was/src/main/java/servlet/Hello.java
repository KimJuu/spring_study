package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class Hello implements SimpleServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath());
  }
}
