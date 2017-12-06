package com.evolvus.abk.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@ComponentScan(basePackages = "com.evolvus")
@SpringBootApplication
@PropertySource(ignoreResourceNotFound = false, value = {"file:${SPRING_CONFIG_LOCATION}/application.properties","classpath:/application.properties"})
@EnableEncryptableProperties
public class FtpKuwaitApp {
	
	@Value("${CORS_ORIGIN}")
	private String corsOrigin;
	
	public static void main(String[] args) {
		System.out.println("Starting the Kuwait FTP application.");
		SpringApplication.run(FtpKuwaitApp.class, args);
		System.out.println("Started Kuwait FTP successfully.");
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				if(!corsOrigin.contains(",")) {
					registry.addMapping("/**").allowedOrigins(corsOrigin);
				} else {
					String[] origins = corsOrigin.split(",");
					for(String origin : origins) {
						registry.addMapping("/**").allowedOrigins(origin);
					}
				}
			}
		};
	}
	
	
	
}
