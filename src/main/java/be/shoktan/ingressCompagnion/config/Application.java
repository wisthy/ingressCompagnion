package be.shoktan.ingressCompagnion.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}
}