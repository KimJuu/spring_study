# 웹 애플리케이션 서버
## 스펙
###1. HTTP/1.1 의 Host 헤더를 해석하세요.
   o 예를 들어, a.com 과 b.com 의 IP 가 같을지라도 설정에 따라 서버에서 다른 데이터를
   제공할 수 있어야 합니다.
   o 아파치 웹 서버의 VirtualHost 기능을 참고하세요.
- a.com, b.com 두 도메인에 대한 root directory 구분하여 설정파일에 저장.(config.json)
- Config 객체 내에서만 설정파일을 접근하고 호출할 수 있도록 책임할당.
- HttpResponse.java 의 forward 메서드에서 request 객체의 host를 조회하여 returnPage 할당.

###2. 다음 사항을 설정 파일로 관리하세요.
   o 파일 포맷은 JSON 으로 자유롭게 구성하세요.<br>
   o 몇 번 포트에서 동작하는지<br>
   o HTTP/1.1 의 Host 별로<br>
   ▪ HTTP_ROOT 디렉터리를 다르게<br>
   ▪ 403, 404, 500 오류일 때 출력할 HTML 파일 이름<br>
- config.json 설정파일에서 port, HTTP_ROOT, errorPage 을 관리.
- Config 객체 내에서만 설정파일을 접근하고 호출할 수 있도록 책임할당.
###3. 403, 404, 500 오류를 처리합니다.
   o 해당 오류 발생 시 적절한 HTML 을 반환합니다.
   o 설정 파일에 적은 파일 이름을 이용합니다.
- HttpResponse.java의 forward 메소드 수행 시 에러상황에 대한 유효성 검증 후 관련 에러 발생 시 Config.java 내 메서드를 통해 에러페이지 리턴.
###4. 다음과 같은 보안 규칙을 둡니다.
   o 다음 규칙에 걸리면 응답 코드 403 을 반환합니다.
   ▪ HTTP_ROOT 디렉터리의 상위 디렉터리에 접근할 때,
   예, http://localhost:8000/../../../../etc/passwd
   ▪ 확장자가 .exe 인 파일을 요청받았을 때
   o 추후 규칙을 추가할 것을 고려해주세요.
- HttpResponse.java의 forward 메소드 수행 시 isForbidden메서드를 통해 403에러 체크.

###5. logback 프레임워크 http://logback.qos.ch/를 이용하여 다음의 로깅 작업을 합니다.
   o 로그 파일을 하루 단위로 분리합니다.<br>
   o 로그 내용에 따라 적절한 로그 레벨을 적용합니다.<br>
   o 오류 발생 시, StackTrace 전체를 로그 파일에 남깁니다.<br>
- pom.xml 에 logback 관련 dependency 추가.
- logback.xml 에 RollingFileAppender 을 구현하여 로그 단위를 하루 단위로 분리.(logs/backup/error/error-%d{yyyy-MM-dd}.log)
- ERROR, INFO 별 구현
- 오류 발생 시 logger.error("에러 정의 ", e); 형태로 구현하여 StackTrace 전체를 로그파일에 남김.
- TEST URL : a.com:8000/Hellonotfound
###6. 간단한 WAS 를 구현합니다.
   o 다음과 같은 SimpleServlet 구현체가 동작해야 합니다.<br>
   ▪ 다음 코드에서 SimpleServlet, HttpRequet, HttpResponse 인터페이스나<br>
   객체는 여러분이 보다 구체적인 인터페이스나 구현체를 제공해야 합니다. 표준<br>
   Java Servlet 과는 무관합니다.<br><br>
   public Hello implements SimpleServlet {<br>
   public void service(HttpRequest req, HttpResponse res) {<br>
   java.io.Writer writer = res.getWriter()<br>
   writer.write("Hello, ");<br>
   writer.write(req.getParameter("name"));<br>
   }<br>
   }<br>
   o URL 을 SimpleServlet 구현체로 매핑합니다. 규칙은 다음과 같습니다.<br>
   ▪ http://localhost:8000/Hello --> Hello.java 로 매핑<br>
   ▪ http://localhost:8000/service.Hello --> service 패키지의 Hello.java 로 매핑<br>
   o 과제는 URL 을 바로 클래스 파일로 매핑하지만, 추후 설정 파일을 이용해서 매핑하는 것도<br>
   고려해서 개발하십시오.<br>
   ▪ 추후 확장을 고려하면 됩니다. 설정 파일을 이용한 매핑을 구현할 필요는 없습니다.<br>
   ▪ 설정 파일을 이용한 매핑에서 사용할 수 있는 설정의 예, {“/Greeting”: “Hello”,
   “/super.Greeting”: “service.Hello”}
- 개인 로컬 PC 의 host파일을 변경(127.0.0.1 a.com, 127.0.0.1 b.com 추가)
- a.com:8000/Hello, a.com:8000/service.Hello, b.com:8000/Hello, b.com:8000/service.Hello 별로 호출하여 테스트 가능.
- ServletMappingInfo enum 클래스로 servlet을 생성하도록 구현하여 추가적인 servlet은 ServletMappingInfo 에서 책임을 가질 수 있도록 구현.
###7. 현재 시각을 출력하는 SimpleServlet 구현체를 작성하세요.
   o 앞서 구현한 WAS 를 이용합니다.
   o WAS 와 SimpleServlet 인터페이스를 포함한 SimpleServlet 구현 객체가 하나의 JAR 에
   있어도 괜찮습니다.
   ▪ 분리하면 더 좋습니다.
- CurrentDateServlet.java 로 ServletMappingInfo.java에서 맵핑된 URL으로 호출하여 현재시각을 log로 확인할 수 있도록 구현.
- 호출 URL : a.com:8000/currentDate 
###8. 앞에서 구현한 여러 스펙을 검증하는 테스트 케이스를 JUnit4 를 이용해서 작성하세요.
- Spec1Test : getReturnPageTest() 메서드 host, url 을 파라미터로 호출 시 returnPage 정보 값 테스트
- Spec2Test : 설정정보 불러오는 메서드 테스트
- Spec3Test : 에러코드 별 에러페이지 리턴 테스트
- Spec4Test : 
- Spec5Test : target/logs 경로의 파일 확인
- Spec6Test : ServletMappingTest() 메서드, path 별로 ServletMapping에서 제대로 servlet이 맵핑되는지 테스트
- Spec7Test : 현재 시간을 정확히 추출하는지 테스트(초단위로 테스트되기 때문에 실패할수도 성공할수도 있으나 제 기능이 수행되는지 확인)
