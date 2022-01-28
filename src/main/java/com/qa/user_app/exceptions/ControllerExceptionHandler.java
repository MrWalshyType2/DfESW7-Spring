package com.qa.user_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = { UserNotFoundException.class })
	public ResponseEntity<String> userNotFoundExceptions(UserNotFoundException unfe) {
		// Spring will automatically pass the UserNotFoundException to this method when it is thrown
		return new ResponseEntity<String>(unfe.getMessage(), HttpStatus.NOT_FOUND);
	}
}
