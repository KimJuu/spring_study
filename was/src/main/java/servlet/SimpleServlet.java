package servlet;

import http.HttpRequest;
import http.HttpResponse;

public interface SimpleServlet {
  void service(HttpRequest httpRequest, HttpResponse httpResponse);
  void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
  void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
