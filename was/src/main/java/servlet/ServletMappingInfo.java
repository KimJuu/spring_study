package servlet;

public enum ServletMappingInfo {
  Hello("/Hello", Hello::new),
  serviceHello("/service.Hello", servlet.service.Hello::new),
  currentDate("/currentDate", CurrentDateServlet::new);

  private String path;
  private ServletCreator creator;

  ServletMappingInfo(String path, ServletCreator creator) {
    this.path = path;
    this.creator = creator;
  }

  public String getPath() {
    return path;
  }

  public ServletCreator getCreator() {
    return creator;
  }
}