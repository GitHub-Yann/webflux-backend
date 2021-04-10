package com.yann.demo.handler;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Component
@Order(-2)//调整优先级，要不然不会被这个抓到
public class ExceptionHandler implements WebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		// 设置响应头
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		// 设置返回类型
		response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
		// 设置异常信息
		String errorMsg = toStr(ex);
		
		DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());
		return response.writeWith(Mono.just(db));
	}
	
	private String toStr(Throwable ex) {
		// 已知异常
		if(ex instanceof CheckException) {
			CheckException e = (CheckException)ex;
			return e.getFieldName()+": invalid value "+e.getFieldValue();
		}
		// 未知异常
		else {
			ex.printStackTrace();
			return ex.toString();
		}
	}

}
