package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
 //  @ExceptionHandler(ResourceNotFoundException.class)
 //  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
 //      ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
 //      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
 //  }

 //  @ExceptionHandler(Exception.class)
 //  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
 //      ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
 //      return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
 //  }
}