package dev.joseph.playground.thymeleaf_demo;

// import org.hibernate.cfg.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
// import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ThymeleafDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ThymeleafDemoApplication.class, args);
		String portNumber = context.getBean(org.springframework.core.env.Environment.class).getProperty("server.port");
		System.out.println(ThymeleafDemoApplication.class.getSimpleName());
		System.out.println(ThymeleafDemoApplication.class.getSimpleName());
		System.out.println(portNumber);
	}

}
