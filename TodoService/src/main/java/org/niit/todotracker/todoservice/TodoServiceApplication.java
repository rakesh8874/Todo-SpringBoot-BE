package org.niit.todotracker.todoservice;

import org.niit.todotracker.todoservice.filter.FilterJwtToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class TodoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoServiceApplication.class, args);
	}

	//Intercepting Urls
	@Bean
	FilterRegistrationBean filterToken(){
		FilterRegistrationBean frb = new FilterRegistrationBean<>();
		frb.setFilter(new FilterJwtToken());
		frb.addUrlPatterns("/userTodo/addCategory/*",
				"/userTodo/current-user/*",
				"/userTodo/addTodo/*",
				"/userTodo/addCategory/*",
				"/userTodo/categories/*",
				"/userTodo/todos/*",
				"/userTodo/addTodoIntoCategory/*",
				"/userTodo/addTodosCategory/*",
				"/userTodo/getTodosOfCategory/*",
				"/userTodo/getSingleTodo/*",
				"/userTodo/updateUser/*",
				"/userTodo/updateTodo/*",
				"/userTodo/deleteTodo/*",
				"/userTodo/deleteCategory/*",
				"/userTodo/deleteTodoFromCategory/*"
				);
		return frb;
	}
}
