package me.lucaskaminski.todolist.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandlerController {
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handle(HttpMessageNotReadableException exception) {
    return ResponseEntity.badRequest().body(exception.getMostSpecificCause().getMessage());
  }
}
