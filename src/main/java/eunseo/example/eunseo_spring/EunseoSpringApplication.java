package eunseo.example.eunseo_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EunseoSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EunseoSpringApplication.class, args);
	}
//라이브러리에 내장된 tomcat 웹 서버를 띄우면서 스트링 부트 서버도 웹서버 위에서 동작함
}
