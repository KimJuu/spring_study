import config.Config;
import org.junit.Assert;
import org.junit.Test;

public class Spec2Test {

  /**
   * 다음 사항을 설정 파일로 관리하세요.
   * o 파일 포맷은 JSON 으로 자유롭게 구성하세요.
   * o 몇 번 포트에서 동작하는지
   * o HTTP/1.1 의 Host 별로
   * ▪ HTTP_ROOT 디렉터리를 다르게
   * ▪ 403, 404, 500 오류일 때 출력할 HTML 파일 이름
   */

  @Test
  public void getPortTest(){
    Assert.assertEquals(Config.getPort(), 8000);
  }

  @Test
  public void getHttpRootTest(){
    String host = "a.com";
    Assert.assertEquals(Config.getHttpRoot(host), "a_com_root/");
    host = "b.com";
    Assert.assertEquals(Config.getHttpRoot(host), "b_com_root/");
  }

  @Test
  public void getErrorPageTest(){
    Assert.assertEquals(Config.getErrorPage("403"), "error/403.html");
    Assert.assertEquals(Config.getErrorPage("404"), "error/404.html");
    Assert.assertEquals(Config.getErrorPage("500"), "error/500.html");
  }
}
