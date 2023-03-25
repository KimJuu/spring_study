package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestHeader;
import http.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
            RequestHeader requestHeader = RequestHeader.parse(bufferedReader);
            String requestBody = null;
            if (requestLine.isPost()) {
                requestBody = IOUtils.readData(bufferedReader, requestHeader.getContentLength());
            }

            HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
            SimpleServlet servlet;

            switch (httpRequest.getPath()){
                case "hello" :
                    servlet = new Hello();
                    break;
                case "service.hello":
                    servlet = new servlet.service.Hello();
                    break;
                default:
                    servlet = new Hello();
                    break;
            }

            servlet.service(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}