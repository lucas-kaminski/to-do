package me.lucaskaminski.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @PostMapping("/")
  public void create(@RequestBody UserModel user) {

    System.out.println(user.getUsername());
    System.out.println(user.getName());
    System.out.println(user.getPassword());
  }

}
