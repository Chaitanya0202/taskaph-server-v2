package com.apixelhouse.taskaph.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;





@ControllerAdvice
public class GlobalExceptionHandler {
	
	  @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFoundException
	    (UserNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(),
	        		HttpStatus.NOT_FOUND);
	    }
	
	
	 @ExceptionHandler(ContactNotFoundException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public ResponseEntity<String> handleUserNotFoundException(ContactNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	 
//	 @ExceptionHandler(UserNotFoundException.class)
//	    @ResponseStatus(HttpStatus.NOT_FOUND)
//	    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//	    }
	  

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGenericException(Exception ex) {
	        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
