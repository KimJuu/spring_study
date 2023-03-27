package http;

import config.Config;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NON_STATIC_RESOURCE_PATH_PREFIX = "/templates/";
    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response403Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 404 \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error("response404Header IOException - ", e);
        }
    }

    private void response500Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 500 \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error("response500Header IOException - ", e);
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.close();
        } catch (IOException e) {
            logger.error("responseBody IOException - ", e);
        }
    }

    private boolean isForbidden(String hostName, String requestURI) {
        Path requestedPath = Paths.get(hostName + requestURI).normalize();
        Path httpRootPath = Paths.get(hostName).normalize();

        return !requestedPath.startsWith(httpRootPath) || requestedPath.toString().endsWith(".exe") || requestURI.contains("\\.\\.\\/");
    }

    public void forward(HttpRequest request) {
        String url = request.getPath();
        String host = request.getHost();
        String hostName = host.split(":")[0];
        String rootDirectory = Config.getHttpRoot(hostName);
        try {
            if(isForbidden(hostName, url)){
                logger.error("403 ERROR : " + NON_STATIC_RESOURCE_PATH_PREFIX + Config.getErrorPage("403"));
                byte[] body = FileIoUtils.loadFileFromClasspath(NON_STATIC_RESOURCE_PATH_PREFIX + Config.getErrorPage("403"));
                response403Header(body.length);
                responseBody(body);
                return;
            }

            if("404".equals(Config.getUrlToHtml(url))){
                logger.error("404 ERROR : " + NON_STATIC_RESOURCE_PATH_PREFIX + Config.getErrorPage("404"));
                byte[] body = FileIoUtils.loadFileFromClasspath(NON_STATIC_RESOURCE_PATH_PREFIX + Config.getErrorPage("404"));
                response404Header(body.length);
                responseBody(body);
            }else{
                logger.info("RETURN PAGE : " + NON_STATIC_RESOURCE_PATH_PREFIX + rootDirectory + Config.getUrlToHtml(url));
                byte[] body = FileIoUtils.loadFileFromClasspath(NON_STATIC_RESOURCE_PATH_PREFIX + rootDirectory + Config.getUrlToHtml(url));
                response200Header(body.length);
                responseBody(body);
            }

        } catch (IOException e) {
            logger.error("forward IOException error",e);
        } catch (URISyntaxException e) {
            logger.error("forward URISyntaxException error", e);
        }
    }
}
