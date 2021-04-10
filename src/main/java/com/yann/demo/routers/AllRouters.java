package com.yann.demo.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.yann.demo.handler.UserHandler;

@Configuration
public class AllRouters {// 有点类似spring mvc的dispatch

	@Bean
	RouterFunction<ServerResponse> userRouter(UserHandler handler){
		// 相当于之前controller里面的 @RequestMapping("/user")
		return RouterFunctions.nest(RequestPredicates.path("/user"), 
				// 相当于类里面的 @RequestMapping或者@GetMapping之类的
				// 获取用户
				RouterFunctions.route(RequestPredicates.GET("/"), handler::getAllUser)
					// 创建用户
					.andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::createUser)
					// 删除用户
					.andRoute(RequestPredicates.DELETE("/{id}"), handler::deleteUser)
					// 根据id获取用户
					.andRoute(RequestPredicates.GET("/{id}"), handler::getUserById));
	}
}
