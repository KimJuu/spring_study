import config.Config;
import org.junit.Assert;
import org.junit.Test;

public class Spec1Test {
  @Test
  public void getReturnPageTest(){
    /**
     * HTTP/1.1 의 Host 헤더를 해석하세요.
     * 예를 들어, a.com 과 b.com 의 IP 가 같을지라도 설정에 따라 서버에서 다른 데이터를
     * 제공할 수 있어야 합니다.
     * 아파치 웹 서버의 VirtualHost 기능을 참고하세요.
     */

    String host = "a.com";
    String url = "/Hello";
    Assert.assertEquals(returnPage(host, url), "a_com_root/hello.html");

    host = "b.com";
    url = "/service.Hello";
    Assert.assertEquals(returnPage(host, url), "b_com_root/serviceHello.html");

  }

  public String returnPage(String host, String url){
    return Config.getHttpRoot(host) + Config.getUrlToHtml(url);
  }
}
