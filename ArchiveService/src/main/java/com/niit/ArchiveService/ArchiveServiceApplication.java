package com.niit.ArchiveService;

import com.niit.ArchiveService.filter.FilterJwtToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ArchiveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchiveServiceApplication.class, args);
	}

	//Intercepting Urls

	@Bean
	FilterRegistrationBean filterToken(){
		FilterRegistrationBean frb = new FilterRegistrationBean<>();
		frb.setFilter(new FilterJwtToken());
		frb.addUrlPatterns("/archiveService/addCompletedTodos/*",
				"/archiveService/allTodos/*",
				"/archiveService/deleteTodo/*",
				"/archiveService/addArchivedTodo/*",
                "/archiveService/archivedTodos/*",
				"/archiveService/completedTodos/*"
		);
		return frb;
	}

}
