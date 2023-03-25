package http;

public class HttpRequest {

  private RequestLine requestLine;
  RequestHeader requestHeader;

  public HttpRequest(RequestLine requestLine, RequestHeader requestHeader) {
    this.requestLine = requestLine;
    this.requestHeader = requestHeader;
  }

  public String getPath() {
    return requestLine.getPath();
  }

}
