import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;
import servlet.CurrentDateServlet;
import servlet.SimpleServlet;

public class Spec7Test {
  @Test
  public void currentDateTest(){
    /**
     * o 앞서 구현한 WAS 를 이용합니다.
     *    o WAS 와 SimpleServlet 인터페이스를 포함한 SimpleServlet 구현 객체가 하나의 JAR 에
     *    있어도 괜찮습니다.
     *    ▪ 분리하면 더 좋습니다.
     * - CurrentDateServlet.java 로 ServletMappingInfo.java에서 맵핑된 URL으로 호출하여 현재시각을 log로 확인할 수 있도록 구현.
     */

    String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Assert.assertEquals(dateTime, CurrentDateServlet.currentDate());
  }

}
