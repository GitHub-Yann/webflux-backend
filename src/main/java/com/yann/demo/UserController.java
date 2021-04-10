//package com.yann.demo;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yann.demo.domain.User;
//import com.yann.demo.domain.repository.UserRepository;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//public class UserController {
//
//	private UserRepository repository;
//
//	public UserController(UserRepository repository) {
//		this.repository = repository;
//	}
//
//	@GetMapping(value = "/all")
//	public Flux<User> getAll() {// 老模式，直接返回所有，以数组形式一次性返回
//		return this.repository.findAll();
//	}
//
//	@GetMapping(value = "/stream/all", produces = "text/event-stream")
//	public Flux<User> streamGetAll() {// 新模式，以SSE形式多次返回数据
//		return this.repository.findAll();
//	}
//
//	@PostMapping("/add")
//	public Mono<User> createUser(@RequestBody User user) {
//		return this.repository.save(user);
//	}
//	
//	@GetMapping("/get/{id}")
//	public Mono<ResponseEntity<User>> getUser(@PathVariable("id") String id) {
//		return this.repository.findById(id)
//				.map(u->new ResponseEntity<User>(u,HttpStatus.OK))
//				.defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
//		return this.repository.findById(id)
//				//当你要操作数据，并返回一个Mono，这个时候使用flatMap
//				//如果不操作数据，只是转换数据，使用map
//				.flatMap(user -> this.repository.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
//				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
//	}
//
//	@PutMapping("/update/{id}")
//	public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id,@RequestBody User user) {
//		return this.repository.findById(id)
//				// flatMap操作数据
//				.flatMap(u -> {
//					u.setAge(user.getAge());
//					u.setName(user.getName());
//					return this.repository.save(u);
//				})
//				// map 转换数据
//				.map(u-> new ResponseEntity<User>(u,HttpStatus.OK))
//				.defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
//	}
//	
//	@GetMapping("/age/{start}/{end}")
//	public Flux<User> findByAge(@PathVariable("start") int start,@PathVariable("end") int end){
//		return this.repository.findByAgeBetween(start, end);
//	}
//	
//	@GetMapping(value="/stream/age/{start}/{end}",produces=MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<User> findByAgeStream(@PathVariable("start") int start,@PathVariable("end") int end){
//		return this.repository.findByAgeBetween(start, end);
//	}
//}
