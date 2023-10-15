package me.lucaskaminski.todolist.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentExceptionHandlerController {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handle(IllegalArgumentException exception) {
    System.out.println("handler");
    return ResponseEntity.badRequest().body(exception.getMessage());
  }
}
