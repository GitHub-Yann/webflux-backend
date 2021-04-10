//package com.yann.demo.domain.repository;
//
//import org.springframework.data.mongodb.repository.Query;
//import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//import org.springframework.stereotype.Repository;
//
//import com.yann.demo.domain.User;
//
//import reactor.core.publisher.Flux;
//
//@Repository
//public interface UserRepository extends ReactiveMongoRepository<User, String> {
//	
//	// spring 的jpa会根据方法名生成相应的sql
//	Flux<User> findByAgeBetween(int start,int end);
//	
//	//mongoDB的语法
//	@Query("{'age':{'$gte':20,'$lte':20}}")
//	Flux<User> oldUser();
//}
