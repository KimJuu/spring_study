import config.Config;
import org.junit.Assert;
import org.junit.Test;

public class Spec3Test {

  @Test
  public void ErrorPageTest() {
    /**
     * 403, 404, 500 오류를 처리합니다.
     * o 해당 오류 발생 시 적절한 HTML 을 반환합니다.
     * o 설정 파일에 적은 파일 이름을 이용합니다
     */
    Assert.assertEquals(Config.getErrorPage("404"), "error/404.html");
  }
}
