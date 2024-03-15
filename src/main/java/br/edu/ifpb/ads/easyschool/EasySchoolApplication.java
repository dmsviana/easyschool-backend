package br.edu.ifpb.ads.easyschool;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.edu.ifpb.ads.easyschool.usecases.InsertAdminUser;
import lombok.RequiredArgsConstructor;


@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@EnableAsync
@RequiredArgsConstructor
public class EasySchoolApplication {

	private final InsertAdminUser insert;

	public static void main(String[] args) {
		SpringApplication.run(EasySchoolApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase(){
		return insert::insertAdminUser;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
