package com.yann.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS");

	// 这个请求在 这个controller里面占用了5秒钟，那么这个线程就占用了5秒钟时间
	@GetMapping("/yann01")
	private String get1() {
		System.out.println(sdf.format(new Date()) + " enter yann01");
		String result = createStr("yann01");
		System.out.println(sdf.format(new Date()) + " exit yann01");
		return result;
	}

	// 由于没有调流的最终操作，不会阻塞线程的操作，这个操作是一个惰性求值
	@GetMapping("/yann02")
	private Mono<String> get2() {
		System.out.println(sdf.format(new Date()) + " enter yann02");
		Mono<String> result = Mono.fromSupplier(() -> createStr("yann02"));
		System.out.println(sdf.format(new Date()) + " exit yann02");
		return result;
	}

	// Flux
	@GetMapping(value = "/yann03", produces = "text/event-stream")
	private Flux<String> get3() {
		System.out.println(sdf.format(new Date()) + " enter yann03");
		Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "flux data --" + i;
		}));
		System.out.println(sdf.format(new Date()) + " exit yann03");
		return result;
	}

	@SuppressWarnings("finally")
	private String createStr(String str) {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			return "result for " + str;
		}
	}
}
