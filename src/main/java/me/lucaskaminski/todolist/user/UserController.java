package me.lucaskaminski.todolist.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity<?> create(@RequestBody UserModel user) {
    var userExists = this.userRepository.findByUsername(user.getUsername());

    if (userExists != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
    }

    var passwordHash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

    user.setPassword(passwordHash);

    var userCreated = this.userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @GetMapping("/")
  public ResponseEntity<?> get(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");

    if (idUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
    }

    UUID userId = UUID.fromString(idUser.toString());
    var current_user = this.userRepository.findById(userId).orElse(null);
    return ResponseEntity.status(HttpStatus.OK).body(current_user);
  }

  @PutMapping("/")
  public ResponseEntity<?> update(@RequestBody UserModel user, HttpServletRequest request) {
    String idUser = request.getAttribute("idUser").toString();

    System.out.println(idUser);

    if (idUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
    }

    UUID uuidUser = UUID.fromString(idUser);
    var current_user = this.userRepository.findById(uuidUser).orElse(null);

    if (current_user == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
    }

    current_user.setName(user.getName());
    current_user.setUsername(user.getUsername());

    var userUpdated = this.userRepository.save(current_user);
    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
  }

  @DeleteMapping("/")
  public ResponseEntity<?> delete(HttpServletRequest request) {
    UUID userId = UUID.fromString(request.getAttribute("idUser").toString());
    var current_user = this.userRepository.findById(userId).orElse(null);

    if (current_user == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
    }

    this.userRepository.delete(current_user);
    return ResponseEntity.status(HttpStatus.OK).body("User deleted");
  }
}
