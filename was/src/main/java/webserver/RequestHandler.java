package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestHeader;
import http.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.DefaultServlet;
import servlet.Hello;
import servlet.SimpleServlet;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        //logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
            RequestHeader requestHeader = RequestHeader.parse(bufferedReader);

            HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            SimpleServlet servlet;

            logger.debug("httpRequest.getPath() : " + httpRequest.getPath());


            switch (httpRequest.getPath()){
                case "/Hello" :
                    servlet = new Hello();
                    break;
                case "/service.Hello":
                    servlet = new servlet.service.Hello();
                    break;
                default:
                    servlet = new DefaultServlet();
                    break;
            }

            servlet.service(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}