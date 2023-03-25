package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class DefaultServlet implements SimpleServlet{

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }
}
