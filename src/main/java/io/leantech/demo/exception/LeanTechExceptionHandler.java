package io.leantech.demo.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LeanTechExceptionHandler {
	
	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handleLeanTechException(ApiRequestException e) {
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		LeanTechException exception = new LeanTechException(e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(exception, badRequest);
	}
	
	@ExceptionHandler(value = {NotFoundRequestException.class})
	public ResponseEntity<Object> handleNotFoundException(NotFoundRequestException e) {
		HttpStatus notFoundRequest = HttpStatus.NOT_FOUND;
		LeanTechException exception = new LeanTechException(e.getMessage(), notFoundRequest, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(exception, notFoundRequest);
	}

}
