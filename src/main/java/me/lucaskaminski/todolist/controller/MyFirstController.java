package me.lucaskaminski.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first")
public class MyFirstController {

  @GetMapping("/message")
  public String firstMessage() {
    return "Hello World!";
  }

}
