package com.yann.demo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.yann.demo.domain.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {//对应之前的controller
	
//	private UserRepository repository;
//
//	public UserHandler(UserRepository repository) {
//		this.repository = repository;
//	}

	public Mono<ServerResponse> getAllUser(ServerRequest request){
		System.out.println("invoke getAllUser");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Flux.just(new User("001","001",10),new User("002","002",20),new User("003","003",30)),User.class);
	}
	
	public Mono<ServerResponse> getUserById(ServerRequest request){
		String id = request.pathVariable("id");
		System.out.println("invoke getUserById id="+id);
		if("001".equals(id)) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(new User("001","001",10)),User.class);
		}else {
			return ServerResponse.notFound().build();
		}
	}
	
	public Mono<ServerResponse> deleteUser(ServerRequest request){
		String id = request.pathVariable("id");
		System.out.println("invoke deleteUser id="+id);
		return ServerResponse.ok().build();
	}
	
	public Mono<ServerResponse> createUser(ServerRequest request){
		Mono<User> user = request.bodyToMono(User.class);
		return user.flatMap(u->{
			System.out.println("invoke createUser id="+u.getId()+", name="+u.getName()+", age="+u.getAge());
			u.setName("xxxxxxxxxxxxxx");
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(Mono.just(u),User.class);
		});
	}
	
//	public Mono<ServerResponse> getAllUser1(ServerRequest request){
//		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
//				.body(repository.findAll(),User.class);
//	}
	
//	public Mono<ServerResponse> createUser123(ServerRequest request){
//		// 通过这个方式获取用户提交的数据
//		Mono<User> user = request.bodyToMono(User.class);
//		return user.flatMap(u->{
//			// 校验代码放在这里
//			// 如果失败，抛出异常，让切面处理
//			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
//					// repository.save方法不支持这个mono或者flux类型的
//					// 这里需要调用saveAll方法，它需要一个发布者，而 mono是一个发布者
//					.body(repository.save(u),User.class);
//		});
//	}
//	
//	public Mono<ServerResponse> deleteUser(ServerRequest request){
//		String id = request.pathVariable("id");
//		return repository.findById(id)
//				.flatMap(user->repository.delete(user)
//						.then(ServerResponse.ok().build()))
//				.switchIfEmpty(ServerResponse.notFound().build());
//	}
}
