import org.junit.Assert;
import org.junit.Test;
import servlet.Hello;
import servlet.ServletMapping;

public class Spec6Test {
  /**
   * 간단한 WAS 를 구현합니다.
   * o 다음과 같은 SimpleServlet 구현체가 동작해야 합니다.
   * ▪ 다음 코드에서 SimpleServlet, HttpRequet, HttpResponse 인터페이스나
   * 객체는 여러분이 보다 구체적인 인터페이스나 구현체를 제공해야 합니다. 표준
   * Java Servlet 과는 무관합니다.
   * public Hello implements SimpleServlet {
   * public void service(HttpRequest req, HttpResponse res) {
   * java.io.Writer writer = res.getWriter()
   * writer.write("Hello, ");
   * writer.write(req.getParameter("name"));
   * }
   * }
   * o URL 을 SimpleServlet 구현체로 매핑합니다. 규칙은 다음과 같습니다.
   * ▪ http://localhost:8000/Hello --> Hello.java 로 매핑
   * ▪ http://localhost:8000/service.Hello --> service 패키지의 Hello.java 로 매핑
   * o 과제는 URL 을 바로 클래스 파일로 매핑하지만, 추후 설정 파일을 이용해서 매핑하는 것도
   * 고려해서 개발하십시오.
   * ▪ 추후 확장을 고려하면 됩니다. 설정 파일을 이용한 매핑을 구현할 필요는
   * 없습니다.
   * ▪ 설정 파일을 이용한 매핑에서 사용할 수 있는 설정의 예, {“/Greeting”: “Hello”,
   * “/super.Greeting”: “service.Hello”}
   */

  @Test
  public void ServletMappingTest(){
    String path = "/Hello";
    Hello hello = new Hello();
    Assert.assertEquals(ServletMapping.getServlet(path).getClass(), hello.getClass());
  }


}
