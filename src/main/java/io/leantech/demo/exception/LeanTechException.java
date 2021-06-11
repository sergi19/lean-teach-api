package io.leantech.demo.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class LeanTechException {
	
	private final String mesagge;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timestamp;
	
	public LeanTechException(String mesagge, HttpStatus httpStatus, ZonedDateTime timestamp) {
		super();
		this.mesagge = mesagge;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}

	public String getMesagge() {
		return mesagge;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

}
