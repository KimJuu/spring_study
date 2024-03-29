package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestHeader;
import http.RequestLine;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.ServletMapping;
import servlet.SimpleServlet;
import utils.IOUtils;

public class RequestHandler implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

  private Socket connection;

  public RequestHandler(Socket connectionSocket) {
    this.connection = connectionSocket;
  }

  public void run() {
    logger.info("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

    try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
      String bufferReadLine = bufferedReader.readLine();
      RequestLine requestLine = RequestLine.parse(bufferReadLine);
      RequestHeader requestHeader = RequestHeader.parse(bufferedReader);
      String requestBody = null;
      if (requestLine.isPost()) {
        requestBody = IOUtils.readData(bufferedReader, requestHeader.getContentLength());
      }
      HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
      logger.info("REQUEST PARAMETER : ", httpRequest.getParameters().toString());
      HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

      SimpleServlet servlet = ServletMapping.getServlet(httpRequest.getPath());
      servlet.service(httpRequest, httpResponse);

    } catch (IOException e) {
      logger.error("RequestHandler IOException error",e);
    }
  }


}