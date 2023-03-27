package servlet;

import http.HttpRequest;
import http.HttpResponse;

public class DefaultServlet implements SimpleServlet{
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
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
