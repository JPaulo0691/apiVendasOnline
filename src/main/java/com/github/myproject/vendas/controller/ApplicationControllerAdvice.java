package com.github.myproject.vendas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.myproject.vendas.exception.OrderNotFounException;
import com.github.myproject.vendas.exception.RegrasException;
import com.github.myproject.vendas.model.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(RegrasException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegrasException(RegrasException e) {
		String mensage = e.getMessage();
		return new ApiErrors(mensage);
	}
	
	@ExceptionHandler(OrderNotFounException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handleOrderNotFoundException(OrderNotFounException e) {
		return new ApiErrors(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleMethoNotValidException(MethodArgumentNotValidException e) {
		
		List<String> errorsList = e.getBindingResult().getAllErrors().stream()
										.map(erro ->
											  erro.getDefaultMessage()
										 ).collect(Collectors.toList());
		return new ApiErrors(errorsList);
	}
}
