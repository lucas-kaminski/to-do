package me.lucaskaminski.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<?> create(@RequestBody TaskModel task, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    task.setIdUser(UUID.fromString(idUser.toString()));

    var currentDateTime = LocalDateTime.now();
    if (currentDateTime.isAfter(task.getStartAt())) {
      return ResponseEntity.badRequest().body("Start date must be after current date");
    }

    if (task.getEndAt().isBefore(task.getStartAt())) {
      return ResponseEntity.badRequest().body("End date must be after start date");
    }

    var createdTask = this.taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

}