package servlet.service;

import http.HttpRequest;
import http.HttpResponse;
import servlet.SimpleServlet;

public class Hello implements SimpleServlet {

  @Override
  public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
    httpResponse.forward(httpRequest.getPath());
  }
}
